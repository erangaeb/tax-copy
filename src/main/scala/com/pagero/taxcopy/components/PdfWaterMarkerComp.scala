package com.pagero.taxcopy.components

import com.pagero.taxcopy.protocols.Attachment

/**
 * Created by eranga on 7/1/16.
 */
trait PdfWaterMarkerComp {

  val pdfWaterMarker: PdfWaterMarker

  trait PdfWaterMarker {
    def addWaterMark(attachment: Attachment, waterMark: String)
  }

}
