package timeoperations

import com.fhs.mensa.SiteParser
import java.util.LinkedList
import scala.collection.JavaConversions._
import com.fhs.mensa.datastorage.HibernateUtil
import java.util.HashSet
import com.fhs.mensa.datastorage.dataclasses.{ Tag, Foot }
import com.fhs.mensa.TagExtractor
import java.util.Calendar
import java.util.GregorianCalendar
import org.hibernate.criterion.Restrictions
import com.fhs.mensa.network.Listener

object CronWorker {
  
  
  
  def getCurrentDate = {
    val theDate: Calendar = new GregorianCalendar
    theDate set (Calendar.HOUR_OF_DAY, 0)
    theDate set (Calendar.MINUTE, 0)
    theDate set (Calendar.SECOND, 0)
    theDate set (Calendar.MILLISECOND, 0)
    theDate
  }
}

class CronWorker extends Runnable {

  def run(): Unit = {
    while (!Thread.interrupted) {
      val allFoot = SiteParser.parseWebsite
      for (e <- allFoot) {
        println(e)
      }

      var toc = new LinkedList[Foot]
      for (e <- allFoot) {
        val foot = new Foot
        foot.setName(new String(e))
        foot.setTags(new HashSet[Tag])
        foot setActiveDay (CronWorker.getCurrentDate)
        toc.add(foot)
      }

      val session = HibernateUtil.getSession
      val tx = session.beginTransaction

      //println(toc)

      for (e <- toc) {
        TagExtractor.extractTags(e, e.getName(), session)

        if (session.createCriteria(classOf[Foot])
            .add(Restrictions.eq("name", e.getName()))
            .add(Restrictions.eq("activeDay", e.getActiveDay())).list.size == 0) {
          session.saveOrUpdate(e)
        }

      }

      //session.flush()
      tx.commit();
      session.close()

      if(!Listener.isActive) {
        Listener.listen
      }
      
      val nextTime = CronWorker.getCurrentDate
      nextTime add (Calendar.DAY_OF_YEAR, 1)
      Thread.sleep(nextTime.getTimeInMillis() - new GregorianCalendar().getTimeInMillis())
    }
  }

}