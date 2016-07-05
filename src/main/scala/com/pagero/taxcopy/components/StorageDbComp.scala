package com.pagero.taxcopy.components

trait StorageDbComp {

  val storageDb: StorageDb

  trait StorageDb {
    def saveAttachment(id: String, attachment: Array[Byte])
  }

}
