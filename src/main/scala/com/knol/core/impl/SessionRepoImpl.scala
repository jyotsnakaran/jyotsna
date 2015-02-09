package com.knol.core.impl

import scala.collection.mutable.MutableList

import com.knol.core._
import com.knol.db.connection.DBConnection

class SessionRepoImpl extends SessionRepo with DBConnection {
  val DB_TOPIC = 1
  val DB_DATE = 2
  val DB_KID = 3
  val DB_ID = 4
  /**
   * create method inserts the new rows in table 'knolx' which references table 'knol'
   * id is the primary key for knolx
   * knol_id is foreign key which is referencing table knol
   */
  def create(session: Session): Option[Int] = {
    val conn = getConnect()
    conn match {
      case Some(conn) =>
        try {
          val prep = conn.prepareStatement("INSERT INTO KNOLX (topic,sdate,knol_id) VALUES (?,?,?) ")
          //  prep.setInt(1, knol.id)
          prep.setString(DB_TOPIC, session.topic)
          prep.setString(DB_DATE, session.date)
          prep.setInt(DB_KID, session.knol_id)
          val rows = prep.executeUpdate
          logger.debug("Rows affected " + rows)
          logger.debug("inserted successfully")
          Some(rows)
        } catch {
          case ex: Exception => None
        }
      case None =>
        None
    }
  }
  /**
   * update method modify the rows of table KNOLX based on the id passed
   */
  def update(session: Session): Option[Int] = {
    val conn = getConnect()
    conn match {

      case Some(conn) =>
        try {

          val prep = conn.prepareStatement("UPDATE KNOLX set topic=?, sdate=?, knol_id=? where id=? ")
          prep.setString(DB_TOPIC, session.topic)
          prep.setString(DB_DATE, session.date)
          prep.setInt(DB_KID, session.knol_id)
          prep.setInt(DB_ID, session.id)
          val rows = prep.executeUpdate
          logger.debug("Rows affected " + rows)
          logger.debug("inserted successfully")
          Some(rows)
        } catch {
          case ex: Exception => None
        } finally {
          conn.close()
        }
      case None =>
        None
    }
  }
  /**
   * delete method delete the rows of table KNOLX based on the id passed
   */
  def delete(id: Int): Option[Int] = {
    val conn = getConnect()
    conn match {
      case Some(conn) =>
        try {
          val prep = conn.prepareStatement("delete from KNOLX where id=?")
          prep.setInt(1, id)
          val rows = prep.executeUpdate
          logger.debug("Rows affected " + rows)
          logger.debug("deleted successfully")
          require(rows >= 1)
          Some(rows)
        } catch {
          case ex: Exception => None
        } finally {
          conn.close()
        }

      case None =>
        None
    }
  }
  /**
   * getById method retrieves the rows of table KNOLX based on the id passed to it.
   */
  def getById(id: Int): Option[Session] = {
    val conn = getConnect()
    conn match {
      case Some(conn) =>
        try {
          val query = "select * from KNOLX where id=" + id + ";"
          val ps = conn.createStatement();
          val set = ps.executeQuery(query);

          set.next()
          val session_id = set.getInt("id")
          val session_topic = set.getString("topic")
          val session_date = set.getString("sdate")
          val session_kid = set.getInt("knol_id")

          //logger.debug(Knol(kmobile, kname, kemail, kid))
          Some(Session(session_topic, session_date, session_kid, session_id))
        } catch {
          case ex: Exception => None
        } finally {
          conn.close()
        }
      case None =>
        None

    }
  }
  /**
   * getlist method retrieves all the rows of table KNOLX
   */
  import scala.collection.mutable.MutableList
  def getList(): Option[MutableList[Session]] = {
    val conn = getConnect()
    val session_list = MutableList[Session]()
    conn match {
      case Some(conn) =>
        val query = "select * from KNOLX;"
        val ps = conn.createStatement();
        val set = ps.executeQuery(query);
        while (set.next()) {
          val session_id = set.getInt("id")
          val session_topic = set.getString("topic")
          val session_date = set.getString("sdate")
          val session_kid = set.getInt("knol_id")
          session_list += (Session(session_topic, session_date, session_kid, session_id))
        }

        Some(session_list)
      case None =>
        None
    }
  }
}

