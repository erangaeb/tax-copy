package com.pagero.taxcopy.components

import com.datastax.driver.core.querybuilder.QueryBuilder
import com.pagero.taxcopy.config.StorageCassandraCluster

/**
 * Created by eranga on 7/4/16.
 */
trait CassandraStorageDbComp extends StorageDbComp {

  this: StorageCassandraCluster =>

  val storageDb = new CassandraStorageDb

  class CassandraStorageDb extends StorageDb {

    override def saveAttachment(id: String, attachment: Array[Byte]) = {
      val statement = QueryBuilder.insertInto("attachment")
        .value("id", id)
        .value("data", id)

      session.execute(statement)
    }

  }

}
