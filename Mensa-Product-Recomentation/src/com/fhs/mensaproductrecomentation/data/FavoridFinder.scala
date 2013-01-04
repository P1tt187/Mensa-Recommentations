package com.fhs.mensaproductrecomentation.data

import scala.collection.JavaConversions._

object FavoridFinder {

  def findFavorite(list: java.util.List[FootRecord]) = {
    if (list.forall(_.tagSum == 0)) {
      null
    } else {
      list.find(_.tagSum == list.map(_.tagSum).max).get
    }
  }

}