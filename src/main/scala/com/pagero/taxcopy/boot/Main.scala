package com.pagero.taxcopy.boot

import akka.actor.ActorSystem
import com.pagero.taxcopy.actors.InputReader
import com.pagero.taxcopy.actors.InputReader.InitReader
import org.slf4j.LoggerFactory

/**
 * Created by eranga on 1/9/16.
 */
object Main extends App {

  def logger = LoggerFactory.getLogger(this.getClass)

  logger.info("Booting application")

  implicit val system = ActorSystem("tax-copy")

  // start input listener
  val inputReader = system.actorOf(InputReader.props(), name = "InputReader")
  inputReader ! InitReader
}
