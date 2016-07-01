package com.pagero.taxcopy.components

/**
 * Created by eranga on 7/1/16.
 */
trait PdfReaderComp {

  val pdfReader: PdfReader

  trait PdfReader {

    def readPdfs(path: String)

  }

}
