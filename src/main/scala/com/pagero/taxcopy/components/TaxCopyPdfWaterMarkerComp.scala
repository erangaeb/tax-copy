package com.pagero.taxcopy.components

import java.io.{ByteArrayOutputStream, FileOutputStream}

import com.itextpdf.text.{Phrase, Font}
import com.itextpdf.text.Element;
import com.itextpdf.text.Font.FontFamily
import com.itextpdf.text.pdf.{ColumnText, PdfContentByte, PdfStamper, PdfReader}

/**
 * Created by eranga on 7/1/16.
 */
trait TaxCopyPdfWaterMarkerComp extends PdfWaterMarkerComp {

  val pdfWaterMarker = new TaxCopyPdfWaterMarker

  class TaxCopyPdfWaterMarker extends PdfWaterMarker {
    override def addWaterMark(pdf: Array[Byte], waterMark: String) {
      val reader: PdfReader = new PdfReader(pdf)
      val stamper: PdfStamper = new PdfStamper(reader, new FileOutputStream(s"/Users/eranga/Desktop/phd-surrey/$waterMark.pdf"))
      //val stamper: PdfStamper = new PdfStamper(reader, new ByteArrayOutputStream())
      val under: PdfContentByte = stamper.getUnderContent(1)

      // text watermark
      val font: Font = new Font(FontFamily.HELVETICA, 50)
      val phrase: Phrase = new Phrase(waterMark, font)

      // find x and y alignments and add phrase
      val x = (reader.getPageSize(1).getLeft + reader.getPageSize(1).getRight) / 2
      val y = (reader.getPageSize(1).getTop + reader.getPageSize(1).getBottom) / 2
      ColumnText.showTextAligned(under, Element.ALIGN_CENTER, phrase, x, y, 0)

      stamper.close()
      reader.close()
    }
  }

}
