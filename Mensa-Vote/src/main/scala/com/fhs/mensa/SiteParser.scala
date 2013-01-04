package com.fhs.mensa

import scala.collection.JavaConversions._
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.WebClient
import java.util.LinkedList
import com.gargoylesoftware.htmlunit.html.HtmlSelect
import com.gargoylesoftware.htmlunit.javascript.host.Event

object SiteParser {
  def parseWebsite: List[String] = {
    val url = "http://www.stw-thueringen.de/deutsch/mensen/einrichtungen/schmalkalden/mensa-schmalkalden.html"

    val webClient = new WebClient(BrowserVersion.FIREFOX_3_6)

    val page: HtmlPage = webClient.getPage(url)

    var pageAsText = page.asText().split("\n")
    //pageAsText.map(e => println(e))
    var relevantLines: java.util.List[String] = new LinkedList[String]
    extractLines(pageAsText, relevantLines)
    if (relevantLines.size() == 0) {
      val select = page.getElementByName("selWeek").asInstanceOf[HtmlSelect]
      select.getOption(1).setSelected(true);
      pageAsText = select.fireEvent(Event.TYPE_CHANGE).getNewPage().asInstanceOf[HtmlPage].asText().split("\n")
      extractLines(pageAsText, relevantLines)
    }

    webClient.closeAllWindows()
    relevantLines.toList
  }

  private def extractLines(pageAsText: Array[java.lang.String], relevantLines: java.util.List[String]): Unit = {

    for (e <- pageAsText) {
      if (e.startsWith("Mittag Ausgabe")) {
        val line = e.substring("Mittag Ausgabe".length() + 3, e.length() - 1).trim()
        if (line.contains("(")) {
          relevantLines.add(line.substring(0, line.indexOf("(")).trim())
        } else {
          relevantLines.add(line)
        }

      }
    }
  }
}