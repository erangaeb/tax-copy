package com.pagero.taxcopy.actors

import akka.actor.SupervisorStrategy.{Stop, Restart}
import akka.actor.{Props, OneForOneStrategy, Actor}
import com.pagero.taxcopy.components.PdfReaderComp
import org.slf4j.LoggerFactory

trait InputHandlerComp {

  this: PdfReaderComp =>

  object InputHandler {

    case class Input(path: String)

    def props(): Props = Props(new InputHandler())
  }

  class InputHandler extends Actor {

    import InputHandler._

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
      case Input(path) =>
        pdfReader.readPdfs(path)
    }
  }

}
