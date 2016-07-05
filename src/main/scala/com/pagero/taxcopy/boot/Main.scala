package com.pagero.taxcopy.boot

import akka.actor.ActorSystem
import com.pagero.taxcopy.actors.EtcdAlarmHandler.InitEtcd
import com.pagero.taxcopy.actors.{EtcdAlarmHandler, RequestListener}
import com.pagero.taxcopy.actors.RequestListener.InitListener
import org.slf4j.LoggerFactory

/**
 * Created by eranga on 1/9/16.
 */
object Main extends App {

  def logger = LoggerFactory.getLogger(this.getClass)

  logger.info("Booting application")

  implicit val system = ActorSystem("taxcopy")

  // start input listener
  val requestListener = system.actorOf(RequestListener.props(), name = "RequestListener")
  requestListener ! InitListener

  // start actor to handle etcd alarms
  val etcdAlarmHandler = system.actorOf(EtcdAlarmHandler.props(), name = "EtcdAlarmHandler")
  etcdAlarmHandler ! InitEtcd
  
}
