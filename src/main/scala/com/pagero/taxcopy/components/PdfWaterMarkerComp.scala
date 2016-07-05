package com.pagero.taxcopy.components

/**
 * Created by eranga on 7/1/16.
 */
trait PdfWaterMarkerComp {

  val pdfWaterMarker: PdfWaterMarker

  trait PdfWaterMarker {
    def addWaterMark(pdf: Array[Byte], waterMark: String)
  }

}
