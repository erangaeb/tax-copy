package com.pagero.taxcopy.components

import java.io.FileOutputStream

import com.itextpdf.text.{Phrase, Font}
import com.itextpdf.text.Element;
import com.itextpdf.text.Font.FontFamily
import com.itextpdf.text.pdf.{ColumnText, PdfContentByte, PdfStamper, PdfReader}

/**
 * Created by eranga on 7/1/16.
 */
trait TaxCopyPdfWaterMarkComp extends PdfWaterMarkComp {

  val pdfWaterMark = new TaxCopyPdfWaterMark

  class TaxCopyPdfWaterMark extends PdfWaterMark {
    override def addWaterMark(pdfContent: Array[Byte]) = {
      val reader: PdfReader = new PdfReader(pdfContent)
      val stamper: PdfStamper = new PdfStamper(reader, new FileOutputStream("/Users/eranga/Desktop/phd-surrey/lambda.pdf"))
      val under: PdfContentByte = stamper.getUnderContent(1);

      // text watermark
      val font: Font = new Font(FontFamily.HELVETICA, 50)
      val phrase: Phrase = new Phrase("Lambda", font)

      // find x and y alignments and add phrase
      val x = (reader.getPageSize(1).getLeft + reader.getPageSize(1).getRight) / 2
      val y = (reader.getPageSize(1).getTop + reader.getPageSize(1).getBottom) / 2
      ColumnText.showTextAligned(under, Element.ALIGN_CENTER, phrase, x, y, 0)

      stamper.close()
      reader.close()
    }
  }

}
