package com.pagero.taxcopy.components

import java.io.File

import com.pagero.taxcopy.protocols.Attachment
import org.slf4j.LoggerFactory

import scala.collection.mutable.ListBuffer

/**
 * Created by eranga on 7/1/16.
 */
trait TaxCopyAttachmentReaderComp extends AttachmentReaderComp {

  def logger = LoggerFactory.getLogger(this.getClass)

  val attachmentReader = new TaxCopyAttachmentReader

  class TaxCopyAttachmentReader extends AttachmentReader {
    override def readAttachments(path: String): ListBuffer[Attachment] = {
      logger.info("Reading pdf file content from " + path)

      val attachments = ListBuffer[Attachment]()

      val files = getFiles(path)
      for (file <- files) {
        val attachment = Attachment(file.getName, readFileContent(file))
        attachments += attachment
      }

      attachments
    }

    def getFiles(dirName: String): List[File] = {
      val dir = new File(dirName)
      if (dir.exists && dir.isDirectory) {
        dir.listFiles.filter(f => f.isFile && f.getName.endsWith(".pdf")).toList
      } else {
        logger.info("Path not exists or not a directory")
        List[File]()
      }
    }

    def readFileContent(file: File): Array[Byte] = {
      logger.info("Read file: " + file.getAbsolutePath)

      val source = scala.io.Source.fromFile(file, "ISO-8859-1")
      val byteArray = source.map(_.toByte).toArray
      source.close()

      logger.info("Read content: " + byteArray.toString)

      byteArray
    }
  }

}
