package com.pagero.taxcopy.actors

import akka.actor.{Actor, Props}
import com.pagero.taxcopy.components.{CassandraStorageDbComp, TaxCopyPdfWaterMarkComp}
import com.pagero.taxcopy.config.StorageCassandraCluster
import com.pagero.taxcopy.protocols.Attachment
import org.slf4j.LoggerFactory

/**
 * Created by eranga on 7/3/16.
 */
object AttachmentHandler {

  case class AttachmentWaterMark(attachment: Attachment, waterMark: String)

  def props(): Props = Props(new AttachmentHandler())

}

class AttachmentHandler extends Actor with TaxCopyPdfWaterMarkComp with CassandraStorageDbComp with StorageCassandraCluster {

  import AttachmentHandler._

  def logger = LoggerFactory.getLogger(this.getClass)

  override def preStart() = {
    logger.info("[_________START ACTOR__________] " + context.self.path)
  }

  override def receive: Receive = {
    case AttachmentWaterMark(attachment, waterMark) =>
      // add watermark
      pdfWaterMark.addWaterMark(attachment.content, waterMark)

      // save in the database
      storageDb.saveAttachment(attachment.content)
  }

}