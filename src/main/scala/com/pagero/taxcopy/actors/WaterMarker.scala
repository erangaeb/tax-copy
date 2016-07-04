package com.pagero.taxcopy.actors

import akka.actor.{Actor, Props}
import com.pagero.taxcopy.components.{CassandraStorageDbComp, TaxCopyPdfWaterMarkComp}
import com.pagero.taxcopy.config.StorageCassandraCluster
import org.slf4j.LoggerFactory

/**
 * Created by eranga on 7/3/16.
 */
object WaterMarker {

  case class PdfWaterMark(pdf: Array[Byte], waterMark: String)

  def props(): Props = Props(new WaterMarker())

}

class WaterMarker extends Actor with TaxCopyPdfWaterMarkComp with CassandraStorageDbComp with StorageCassandraCluster {

  import WaterMarker._

  def logger = LoggerFactory.getLogger(this.getClass)

  override def preStart() = {
    logger.info("[_________START ACTOR__________] " + context.self.path)
  }

  override def receive: Receive = {
    case PdfWaterMark(pdf, waterMark) =>
      // add watermark
      pdfWaterMark.addWaterMark(pdf, waterMark)

      // save in the database
      storageDb.saveAttachment(pdf)
  }

}
