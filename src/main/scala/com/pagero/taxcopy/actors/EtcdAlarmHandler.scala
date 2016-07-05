package com.pagero.taxcopy.actors

import akka.actor.{Actor, Props}
import org.slf4j.LoggerFactory

object EtcdAlarmHandler {

  case class InitEtcd()

  case class LAlarm(message: String)

  case class MAlarm(message: String)

  case class HAlarm(message: String)

  def props(): Props = Props(new EtcdAlarmHandler())

}

class EtcdAlarmHandler extends Actor {

  import EtcdAlarmHandler._

  def logger = LoggerFactory.getLogger(this.getClass)

  override def preStart() = {
    logger.info("[_________START ACTOR__________] " + context.self.path)
  }

  override def receive: Receive = {
    case InitEtcd =>
      logger.info("Init Etcd")
    case LAlarm(message) =>
      logger.error("Low alarm " + message)
    case MAlarm(message) =>
      logger.error("Low alarm " + message)
    case HAlarm(message) =>
      logger.error("Low alarm " + message)
  }

}
