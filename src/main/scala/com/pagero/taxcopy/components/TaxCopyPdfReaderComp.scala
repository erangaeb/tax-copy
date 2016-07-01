package com.pagero.taxcopy.components

/**
 * Created by eranga on 7/1/16.
 */
trait TaxCopyPdfReaderComp extends PdfReaderComp {

  val pdfReader = new TaxCopyPdfReader

  class TaxCopyPdfReader extends PdfReader {
    override def createAcc(path: String) = {

    }
  }

}
