package com.fhs.mensa


import com.fhs.mensa.network.Listener
import java.io.File
import timeoperations.CronWorker


object ServerApp extends App {
  //List(new File("hsql.lck"), new File("hsql.script"), new File("hsql.properties"), new File("hsql.log")).map(file => if (file.exists()) { file.delete() })
  deleteLockFile

  new Thread(new CronWorker) start

  

  private def deleteLockFile() {
    val theFile = new File("hsql.lck");
    if (theFile exists) {
      theFile delete
    }
  }

}

