package com.pagero.taxcopy.actors

import akka.actor.SupervisorStrategy.{Restart, Stop}
import akka.actor.{Actor, OneForOneStrategy, Props}
import org.slf4j.LoggerFactory

object InputReader {

  case class InitReader()

  def props(): Props = Props(new InputReader())
}

class InputReader extends Actor {

  import InputReader._

  def logger = LoggerFactory.getLogger(this.getClass)

  override def preStart() = {
    logger.info("[_________START ACTOR__________] " + context.self.path)
  }

  override def supervisorStrategy = OneForOneStrategy() {
    case e: NullPointerException =>
      logger.error("Null pointer exception caught [RESTART]" + e)
      Restart
    case e: Exception =>
      logger.error("Exception caught, [STOP] " + e)
      Stop
  }

  override def receive: Receive = {
    case InitReader =>
      // listen for user inputs form commandline
      println()
      println()
      println("---------------------------------------")
      println("ENTER PATH[/Users/eranga/workspace/dir]")
      println("---------------------------------------")
      println()

      // read user input from the command line
      val input = scala.io.StdIn.readLine()

      logger.debug("Input : " + input)

      // handle input via handler actor
      val inputHandler = context.actorOf(InputHandler.props())
      inputHandler ! InputHandler.Input(input)
  }
}