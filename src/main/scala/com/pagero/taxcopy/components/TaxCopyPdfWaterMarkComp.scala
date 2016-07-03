package com.pagero.taxcopy.components

/**
 * Created by eranga on 7/1/16.
 */
trait TaxCopyPdfWaterMarkComp extends PdfWaterMarkComp {

  val pdfWaterMark = new TaxCopyPdfWaterMark

  class TaxCopyPdfWaterMark extends PdfWaterMark {
    override def addWaterMark() = {

    }
  }

}
