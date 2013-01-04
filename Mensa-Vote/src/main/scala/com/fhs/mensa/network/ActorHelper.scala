package com.fhs.mensa.network

import scala.collection.JavaConversions._
import org.hibernate.Session
import com.fhs.mensa.datastorage.dataclasses.User
import com.fhs.mensa.datastorage.dataclasses.UserChoise
import com.fhs.mensa.datastorage.DatabaseOperations
import com.fhs.mensa.network.messages.FootContent
import java.util.LinkedList
import com.fhs.mensa.datastorage.dataclasses.FavoriteCount
import com.fhs.mensa.datastorage.HibernateUtil
import com.fhs.mensa.datastorage.dataclasses.Foot
import org.hibernate.criterion.Restrictions
import org.jboss.logging.Logger

object ActorHelper {

  private val logger = Logger.getLogger(ActorHelper.getClass())

  def givePlan(session: Session, userId: String): java.util.List[FootContent] = {
    var planSet = Set[FootContent]()
    DatabaseOperations.getFoot(session).foreach(e => planSet +=
      new FootContent(e.getId().toLong, e.getName().toString(), DatabaseOperations.getMeanVotesForFoot(session, e), getTagsumForFootAndUser(userId, e.getId())))
    return planSet.toList.sortBy(_.getId())
  }

  def getTagsumForFootAndUser(userID: String, footID: Long) = {

    val session = HibernateUtil.getSession()
    val user = DatabaseOperations.getUserByUserID(session, userID)
    val foot = session.createCriteria(classOf[Foot]).add(Restrictions.idEq(footID)).uniqueResult().asInstanceOf[Foot]

    System.err.println("getTagsumForFootAndUser:" + user);
    if (user == null) { 0.asInstanceOf[Short] }
    else {
      foot.getTags().map {
        tag =>
          user.getFavoriteCount().find(_.getTag().getId().equals(tag.getId())) match {
            case Some(fc) => fc.getValue.asInstanceOf[Short]
            case _ => 0.asInstanceOf[Short]
          }
      }.sum
    }

  }

  def vote(session: Session, userid: String, value: Short, footid: Long) {
    val transaction = session.beginTransaction();
    var user: User = DatabaseOperations.getUserByUserID(session, userid)
    if (user == null) {
      user = new User
      user.setUserID(userid)

      session.saveOrUpdate(user)
    }
    val foot = DatabaseOperations.getFootById(session, footid)
    var userChoise = DatabaseOperations.getUserChoiseForFoot(session, user, foot)

    foot.getTags.foreach {
      ft =>
        if (value == 0) {
          user.getFavoriteCount.find(_.getTag().getId().equals(ft.getId())) match {
            case Some(fc) =>
              if (userChoise != null && userChoise.getVote() != value) {
                if (userChoise.getVote() == 2) {
                  fc.incrementCount
                  fc.incrementCount
                } else {
                  fc.incrementCount
                }

                session saveOrUpdate (fc)
              }
            case None =>
              val fc = new FavoriteCount
              fc.setUser(user)
              fc.setTag(ft)

              fc.incrementCount
              fc.incrementCount()
              user.getFavoriteCount add (fc)
              session saveOrUpdate (fc)
            // session saveOrUpdate(user)
          }
        } else if (value == 1) {
          user.getFavoriteCount.find(_.getTag().getId().equals(ft.getId())) match {
            case Some(fc) =>
              if (userChoise != null && userChoise.getVote() < value) {
                if (userChoise.getVote() == 0) {
                  fc.decrementCount

                } else {
                  fc.incrementCount()
                }
                session saveOrUpdate (fc)
              } else if (userChoise.getVote() != value) {
                fc.incrementCount
              }
            case None =>
              val fc = new FavoriteCount
              fc.setUser(user)
              fc.setTag(ft)

              fc.incrementCount

              user.getFavoriteCount add (fc)
              session saveOrUpdate (fc)

          }
        } else if (value == 2) {
          user.getFavoriteCount.find(_.getTag().getId().equals(ft.getId())) match {
            case Some(fc) =>
              if (userChoise != null && userChoise.getVote() < value) {
                if (userChoise != null && userChoise.getVote() != value && userChoise.getVote() == 1) {
                  fc.decrementCount
                  session saveOrUpdate (fc)
                } else if (userChoise != null && userChoise.getVote() != value && userChoise.getVote() == 0) {
                  fc.decrementCount
                  fc.decrementCount
                  session saveOrUpdate (fc)
                }
              }
            case None =>
          }
        }
    }

    session saveOrUpdate (user)

    if (userChoise == null) {
      userChoise = new UserChoise
      userChoise.setUser(user)
      user.getVotes().add(userChoise)
    }
    userChoise.setVote(value)
    userChoise.setFoot(foot)
    session.saveOrUpdate(userChoise)
    session.saveOrUpdate(user)

    transaction.commit();
    session.flush();
  }
}