package com.knol.db.connection

import java.sql.Connection
import java.sql.DriverManager

import org.slf4j.LoggerFactory

import com.typesafe.config.ConfigFactory

trait DBConnection {

  val logger = LoggerFactory.getLogger(this.getClass)

  val config = ConfigFactory.load()
  val url = config.getString("db.url")
  val usr = config.getString("db.usr")
  val password = config.getString("db.password")

  def getConnect(): Option[Connection] =
    {

      try {

        Class.forName("com.mysql.jdbc.Driver")
        val conn = DriverManager.getConnection(url, usr, password)
        logger.debug("connection established")

        Some(conn)
      } catch {
        case ex: Exception =>
          ex.printStackTrace()
          None
      } finally {}

    }

}
