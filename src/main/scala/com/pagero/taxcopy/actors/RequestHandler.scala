package com.pagero.taxcopy.actors

import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, OneForOneStrategy, Props}
import com.pagero.taxcopy.actors.EtcdAlarmHandler.{HAlarm, MAlarm}
import com.pagero.taxcopy.components.TaxCopyAttachmentReaderComp
import org.slf4j.LoggerFactory


object RequestHandler {

  case class Request(path: String)

  def props(): Props = Props(new RequestHandler())

}

class RequestHandler extends Actor with TaxCopyAttachmentReaderComp {

  import RequestHandler._

  def actorLogger = LoggerFactory.getLogger(this.getClass)

  // need to notify etcd alarms on failure
  val etcdAlarmHandler = context.actorSelection("/user/EtcdAlarmHandler")

  override def preStart() = {
    logger.info("[_________START ACTOR__________] " + context.self.path)
  }

  override def supervisorStrategy = OneForOneStrategy() {
    case e: NullPointerException =>
      actorLogger.error("Null pointer exception caught [RESTART]" + e)

      // notify to etcd
      etcdAlarmHandler ! HAlarm(e.getMessage)

      Stop
    case e: Exception =>
      actorLogger.error("Exception caught, [STOP] " + e)

      // notify to etcd
      etcdAlarmHandler ! MAlarm(e.getMessage)

      Stop
  }

  override def receive: Receive = {
    case Request(path) =>
      val attachments = attachmentReader.readAttachments(path)
      for (attachment <- attachments) {
        // start actor to process the pdf
        val waterMarker = context.actorOf(AttachmentHandler.props())
        waterMarker ! AttachmentHandler.AttachmentWaterMark(attachment, "/Users/eranga/Desktop/talk/pagero.png")
      }
  }

}
