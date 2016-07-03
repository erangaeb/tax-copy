package com.pagero.taxcopy.components

import scala.collection.mutable.ListBuffer

/**
 * Created by eranga on 7/1/16.
 */
trait PdfReaderComp {

  val pdfReader: PdfReader

  trait PdfReader {

    def readPdfs(path: String): ListBuffer[Array[Byte]]

  }

}
