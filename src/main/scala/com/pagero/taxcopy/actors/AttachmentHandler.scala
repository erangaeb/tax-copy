package com.pagero.taxcopy.actors

import akka.actor.{Actor, Props}
import com.pagero.taxcopy.components.{CassandraStorageDbComp, TaxCopyPdfWaterMarkerComp}
import com.pagero.taxcopy.config.StorageCassandraCluster
import com.pagero.taxcopy.protocols.Attachment
import org.slf4j.LoggerFactory

import scala.util.Random

/**
 * Created by eranga on 7/3/16.
 */
object AttachmentHandler {

  case class AttachmentWaterMark(attachment: Attachment, waterMark: String)

  def props(): Props = Props(new AttachmentHandler())

}

class AttachmentHandler extends Actor with TaxCopyPdfWaterMarkerComp with CassandraStorageDbComp with StorageCassandraCluster {

  import AttachmentHandler._

  def logger = LoggerFactory.getLogger(this.getClass)

  override def preStart() = {
    logger.info("[_________START ACTOR__________] " + context.self.path)
  }

  override def postStop() = {
    super.postStop()
    logger.info("[_________STOP ACTOR__________] " + context.self.path)
  }

  override def receive: Receive = {
    case AttachmentWaterMark(attachment, waterMark) =>
      // add watermark
      pdfWaterMarker.addWaterMark(attachment, waterMark)

      // save in the database
      val attachmentId = (System.currentTimeMillis / 1000).toString + Random.nextInt(100)
      storageDb.saveAttachment(attachmentId, attachment.content)
  }

}