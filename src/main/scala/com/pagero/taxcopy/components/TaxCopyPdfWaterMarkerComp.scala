package com.pagero.taxcopy.components

import java.io.FileOutputStream

import com.itextpdf.text._
import com.itextpdf.text.pdf._

/**
 * Created by eranga on 7/1/16.
 */
trait TaxCopyPdfWaterMarkerComp extends PdfWaterMarkerComp {

  val pdfWaterMarker = new TaxCopyPdfWaterMarker

  class TaxCopyPdfWaterMarker extends PdfWaterMarker {
    override def addWaterMark(pdf: Array[Byte], waterMark: String) {
      val reader: PdfReader = new PdfReader(pdf)
      val stamper: PdfStamper = new PdfStamper(reader, new FileOutputStream(s"/Users/eranga/Desktop/talk/output/$waterMark.pdf"))
      //val stamper: PdfStamper = new PdfStamper(reader, new ByteArrayOutputStream())

      // image watermark
      val img: Image = Image.getInstance("/Users/eranga/Desktop/talk/pagero.png");
      val imgWight = img.getScaledWidth
      val imgHeight = img.getScaledHeight

      // transparency
      val gState: PdfGState = new PdfGState()
      gState.setFillOpacity(0.3f)

      // watermark width/height
      for (i <- 1 to reader.getNumberOfPages) {
        val over: PdfContentByte = stamper.getOverContent(i)
        val pageSize: Rectangle = reader.getPageSize(i)
        val x = (pageSize.getLeft + pageSize.getRight) / 2
        val y = (pageSize.getTop + pageSize.getBottom) / 2

        // add watermark image
        over.saveState()
        over.setGState(gState)
        over.addImage(img, imgWight, 0, 0, imgHeight, x - (imgWight / 2), y - (imgHeight / 2))
        over.restoreState()
      }

      stamper.close()
      reader.close()
    }
  }

}
