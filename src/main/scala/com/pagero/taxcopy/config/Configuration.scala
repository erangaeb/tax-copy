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

  // dir config
  lazy val imgDirName = Try(config.getString("dir.img.name")).getOrElse("home")
  lazy val pdfDirName = Try(config.getString("dir.pdf.name")).getOrElse("home")
}
