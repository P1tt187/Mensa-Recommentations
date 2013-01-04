package com.fhs.mensa.network
import java.net.ServerSocket
import java.util.concurrent.Executors
import com.fhs.mensa.datastorage.HibernateUtil
import scala.actors.Actor
import java.util.Scanner
import java.util.concurrent.TimeUnit
import org.hsqldb.DatabaseManager

object Listener {

  private var active = false

  private val executor = Executors.newCachedThreadPool

  val server = new ServerSocket(8080)
  
  

  def isActive = active

  // val session = HibernateUtil.getSession

  def listen {
    active = true
    
    println("server startet")

    new KeyActor start ()
    while (true) {
      try {
        val connection = server.accept()
        val newMember = new VotingMensaActor(connection)
        executor.execute(newMember)
        println("connection Acceptet " + connection.getInetAddress() + ":" + connection.getPort())
      } catch {
        case t =>
          return
      }
      //      allActors = newMember :: allActors
    }
  }

  class KeyActor extends Actor {

    override def act = {
      val input = new Scanner(System.in);
      loop {
        if (input.hasNext()) {
          if (input.nextLine().trim().equalsIgnoreCase("q")) {
            executor.shutdown
            if (!executor.awaitTermination(20, TimeUnit.SECONDS)) {
              executor.shutdownNow
            }
            
            /*
            HibernateUtil.shutDown
            org.hsqldb.DatabaseManager.closeDatabases(0)
            */
            HibernateUtil.getSession().createSQLQuery("SHUTDOWN").executeUpdate()
            server.close
            
            
            
            System exit 0
          }
        }
      }
    }

  }
}



