package com.pagero.taxcopy.components

import java.io.File

import org.slf4j.LoggerFactory

/**
 * Created by eranga on 7/1/16.
 */
trait TaxCopyPdfReaderComp extends PdfReaderComp {

  def logger = LoggerFactory.getLogger(this.getClass)

  val pdfReader = new TaxCopyPdfReader

  class TaxCopyPdfReader extends PdfReader {
    override def readPdfs(path: String) = {
      logger.info("Reading pdf file content from " + path)

      val files = getFileNames(path)
      for (file <- files) {
        readFileContent(file)
      }
    }

    def getFileNames(dirName: String): List[File] = {
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
