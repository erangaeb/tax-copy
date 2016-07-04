package com.pagero.taxcopy.components

trait StorageDbComp {

  val storageDb: StorageDb

  trait StorageDb {
    def saveAttachment(attachment: Array[Byte])
  }

}
