package com.pagero.taxcopy.actors

import akka.actor.SupervisorStrategy.{Stop, Restart}
import akka.actor.{Props, OneForOneStrategy, Actor}
import com.pagero.taxcopy.components.{TaxCopyPdfWaterMarkComp, PdfReaderComp}
import org.slf4j.LoggerFactory

import scala.util.Random

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
        val pdfs = pdfReader.readPdfs(path)
        for (pdf <- pdfs) {
          // start actor to process the pdf
          val waterMarkerComp = new WaterMarkerComp with TaxCopyPdfWaterMarkComp
          val waterMarker = context.actorOf(waterMarkerComp.WaterMarker.props())
          waterMarker ! waterMarkerComp.WaterMarker.PdfWaterMark(pdf, "lambda" + Random.nextInt(100))
        }
    }
  }

}
