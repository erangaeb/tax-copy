package com.pagero.taxcopy.actors

import akka.actor.{Props, Actor}
import com.pagero.taxcopy.components.PdfWaterMarkComp
import org.slf4j.LoggerFactory

/**
 * Created by eranga on 7/3/16.
 */
trait WaterMarkerComp {

  this: PdfWaterMarkComp =>

  object WaterMarker {

    case class PdfWaterMark(pdf: Array[Byte], waterMark: String)

    def props(): Props = Props(new WaterMarker())
  }

  class WaterMarker extends Actor {

    import WaterMarker._

    def logger = LoggerFactory.getLogger(this.getClass)

    override def preStart() = {
      logger.info("[_________START ACTOR__________] " + context.self.path)
    }

    override def receive: Receive = {
      case PdfWaterMark(pdf, waterMark) =>
        pdfWaterMark.addWaterMark(pdf, waterMark)
    }
  }

}
