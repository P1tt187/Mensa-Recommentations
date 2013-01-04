/**
 *
 */
package com.fhs.mensa


import com.fhs.mensa.favoritegenerator.EBadWords
import org.hibernate.Session
import java.util.regex.Pattern
import java.util.regex.Matcher
import com.fhs.mensa.datastorage.dataclasses.Tag
import org.hibernate.criterion.Restrictions
import org.jboss.logging.Logger
import com.fhs.mensa.favoritegenerator.ESuffixes
import com.fhs.mensa.datastorage.dataclasses.Foot

/**
 * @author fabian
 *
 */
object TagExtractor {

  private val logger = Logger.getLogger(TagExtractor.getClass())

  def extractTags(foot: Foot, line: String, session: Session) {

    var extractString = line.toLowerCase.replaceAll(",", "").replaceAll("-", " ") + " ";
    for (word <- EBadWords.values) {
      extractString = extractString.replaceAll(" " + word.name().toLowerCase() + " ", " ");
    }
    extractString = extractSuffixes(extractString)

    val tagStrings = extractString.toString().split(" ")
    for (tagString <- tagStrings) {
      var tag = session.createCriteria(classOf[Tag]).add(Restrictions.eq("name", tagString)).uniqueResult().asInstanceOf[Tag]
      if (tag == null && tagString.length > 0) {
        tag = new Tag
        tag.setName(new String(tagString))
        logger.info("Tag: " + tagString)
        //println("Tagextractor: " + tagString)
        foot.getTags().add(tag)
        session.saveOrUpdate(tag)
      } else {
        logger.info("Tagextractor: tag not null or tagstring empty [tag] " + tag + " [tagstring] " + tagString)
      }
    }
  }

  private def extractSuffixes(line: String) = {
    var extractedString = new String(line)
    for (suffix <- ESuffixes.values) {      
      extractedString = extractedString.replaceAll(suffix.toString().toLowerCase() + " ", " ");
    }

    logger.info("extracted String: " + extractedString)
    extractedString
  }

}