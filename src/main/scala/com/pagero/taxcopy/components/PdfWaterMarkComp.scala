package com.pagero.taxcopy.components

/**
 * Created by eranga on 7/1/16.
 */
trait PdfWaterMarkComp {

  val pdfWaterMark: PdfWaterMark

  trait PdfWaterMark {
    def addWaterMark(pdfContent: Array[Byte])
  }

}
