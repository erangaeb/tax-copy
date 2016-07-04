package com.pagero.taxcopy.actors

import akka.actor.SupervisorStrategy.{Restart, Stop}
import akka.actor.{Actor, OneForOneStrategy, Props}
import com.pagero.taxcopy.components.TaxCopyAttachmentReaderComp
import org.slf4j.LoggerFactory

import scala.util.Random


object RequestHandler {

  case class Request(path: String)

  def props(): Props = Props(new RequestHandler())

}

class RequestHandler extends Actor with TaxCopyAttachmentReaderComp {

  import RequestHandler._

  def actorLogger = LoggerFactory.getLogger(this.getClass)

  override def preStart() = {
    logger.info("[_________START ACTOR__________] " + context.self.path)
  }

  override def supervisorStrategy = OneForOneStrategy() {
    case e: NullPointerException =>
      actorLogger.error("Null pointer exception caught [RESTART]" + e)
      Restart
    case e: Exception =>
      actorLogger.error("Exception caught, [STOP] " + e)
      Stop
  }

  override def receive: Receive = {
    case Request(path) =>
      val attachments = attachmentReader.readAttachments(path)
      for (attachment <- attachments) {
        // start actor to process the pdf
        val waterMarker = context.actorOf(AttachmentHandler.props())
        waterMarker ! AttachmentHandler.AttachmentWaterMark(attachment, "lambda" + Random.nextInt(100))
      }
  }

}
