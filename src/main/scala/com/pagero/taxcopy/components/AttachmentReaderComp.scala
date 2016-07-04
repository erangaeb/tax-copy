package com.pagero.taxcopy.components

import com.pagero.taxcopy.protocols.Attachment

import scala.collection.mutable.ListBuffer

/**
 * Created by eranga on 7/1/16.
 */
trait AttachmentReaderComp {

  val attachmentReader: AttachmentReader

  trait AttachmentReader {

    def readAttachments(path: String): ListBuffer[Attachment]

  }

}
