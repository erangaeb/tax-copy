package com.pagero.taxcopy.config

import com.typesafe.config.ConfigFactory

import scala.util.Try

/**
 * Load configurations define in application.conf from here
 *
 * @author eranga herath(erangaeb.herath@pagero.com)
 */
trait Configuration {
  // config object
  val config = ConfigFactory.load()

  // cassandra db config
  lazy val cassandraHost = Try(config.getString("db.cassandra.host")).getOrElse("localhost")
  lazy val cassandraPort = Try(config.getInt("db.cassandra.port")).getOrElse(9160)
}
