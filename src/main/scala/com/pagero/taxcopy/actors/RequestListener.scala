package com.pagero.taxcopy.actors

import akka.actor.SupervisorStrategy.{Restart, Stop}
import akka.actor.{Actor, OneForOneStrategy, Props}
import com.pagero.taxcopy.components.TaxCopyAttachmentReaderComp
import org.slf4j.LoggerFactory

object RequestListener {

  case class InitListener()

  def props(): Props = Props(new RequestListener())

}

class RequestListener extends Actor {

  import RequestListener._

  def logger = LoggerFactory.getLogger(this.getClass)

  override def preStart() = {
    logger.info("[_________START ACTOR__________] " + context.self.path)
  }

  override def supervisorStrategy = OneForOneStrategy() {
    case e: NullPointerException =>
      logger.error("Null pointer exception caught [RESTART]" + e)
      Stop
    case e: Exception =>
      logger.error("Exception caught, [STOP] " + e)
      Restart
  }

  override def receive: Receive = {
    case InitListener =>
      logger.info("Init request listener")

      // listen for user inputs form commandline
      println()
      println()
      println("---------------------------------------")
      println("ENTER PATH[/Users/eranga/workspace/dir]")
      println("---------------------------------------")
      println()

      // read user input from the command line
      val input = scala.io.StdIn.readLine()

      logger.info("Input : " + input)

      // handle request via handler actor
      val requestHandler = new RequestHandlerComp with TaxCopyAttachmentReaderComp
      val inputHandler = context.actorOf(requestHandler.RequestHandler.props())
      inputHandler ! requestHandler.RequestHandler.Request(input)

      // listen again
      self ! InitListener
  }
}