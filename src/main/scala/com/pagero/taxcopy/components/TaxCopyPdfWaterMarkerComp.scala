package com.pagero.taxcopy.components

import java.io.FileOutputStream

import com.itextpdf.text._
import com.itextpdf.text.pdf._
import com.pagero.taxcopy.protocols.Attachment

/**
 * Created by eranga on 7/1/16.
 */
trait TaxCopyPdfWaterMarkerComp extends PdfWaterMarkerComp {

  val pdfWaterMarker = new TaxCopyPdfWaterMarker

  class TaxCopyPdfWaterMarker extends PdfWaterMarker {
    override def addWaterMark(attachment: Attachment, waterMark: String) {
      val reader: PdfReader = new PdfReader(attachment.content)
      val stamper: PdfStamper = new PdfStamper(reader, new FileOutputStream(s"/Users/eranga/Desktop/talk/output/${attachment.name}.pdf"))
      //val stamper: PdfStamper = new PdfStamper(reader, new ByteArrayOutputStream())

      // image watermark
      val img: Image = Image.getInstance(waterMark)
      val imgWight = img.getScaledWidth
      val imgHeight = img.getScaledHeight

      // transparency
      val gState: PdfGState = new PdfGState()
      gState.setFillOpacity(0.2f)

      // add water mark to all pages
      for (i <- 1 to reader.getNumberOfPages) {
        // watermark width/height
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
