package com.pagero.taxcopy.components

import java.io.File

/**
 * Created by eranga on 7/1/16.
 */
trait TaxCopyPdfReaderComp extends PdfReaderComp {

  val pdfReader = new TaxCopyPdfReader

  class TaxCopyPdfReader extends PdfReader {
    override def readPdfs(path: String) = {
      val files = getFileNames(path)
      for (file <- files) {
        getFileContent(file)
      }
    }

    def getFileNames(dirName: String): List[File] = {
      val dir = new File(dirName)
      if (dir.exists && dir.isDirectory) {
        dir.listFiles.filter(_.isFile).toList
      } else {
        List[File]()
      }
    }

    def getFileContent(file: File): Array[Byte] = {
      val source = scala.io.Source.fromFile(file)
      val byteArray = source.map(_.toByte).toArray
      source.close()

      byteArray
    }
  }

}
