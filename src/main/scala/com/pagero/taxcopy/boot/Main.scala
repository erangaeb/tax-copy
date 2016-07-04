package com.pagero.taxcopy.boot

import akka.actor.ActorSystem
import com.pagero.taxcopy.actors.RequestListener
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
  val inputReader = system.actorOf(RequestListener.props(), name = "RequestListener")
  inputReader ! InitListener

  // TODO start actor to handle etcd
}
