package com.knol.core.impl

import org.slf4j.LoggerFactory
import com.knol.core.Knol
import com.knol.core.KnolRepo
import com.knol.db.connection.DBConnection

class KnolRepoImpl extends KnolRepo with DBConnection {

  val DB_NAME = 1
  val DB_EMAIL = 2
  val DB_MOBILE = 3
  val DB_ID = 4
  /**
   * create method inserts the new rows in table 'knol'
   * id is the primary key for knol
   */
  def create(knol: Knol): Option[Int] =
    {
      val conn = getConnect()

      conn match {
        case Some(conn) =>
          try {
            val prep = conn.prepareStatement("INSERT INTO KNOL (name,email,mobile) VALUES (?,?,?) ")
            //  prep.setInt(1, knol.id)
            prep.setString(DB_NAME, knol.name)
            prep.setString(DB_EMAIL, knol.email)
            prep.setString(3, knol.mobile)
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
   * updating the rows of table KNOL based on the id passed
   */
  def update(knol: Knol): Option[Int] =
    {
      val conn = getConnect()

      conn match {

        case Some(conn) =>
          try{
          val prep = conn.prepareStatement("update KNOL set name=?,email=?,mobile=? where id=?")
          prep.setString(DB_NAME, knol.name)
          prep.setString(DB_EMAIL, knol.email)
          prep.setString(DB_MOBILE, knol.mobile)
          prep.setInt(DB_ID, knol.id)
          val rows = prep.executeUpdate
          logger.debug("Rows affected " + rows)
          logger.debug("updated successfully")
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
   * deleting the rows of table KNOL based on the id passed
   */
  def delete(id: Int): Option[Int] = {
    val conn = getConnect()

    conn match {
      case Some(conn) =>
        try{
        val prep = conn.prepareStatement("delete from KNOL where id=?")
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
        logger.debug("Error")
        None

    }
  }
  /**
   * getIntern method retrieves the rows of table KNOL based on the id passed to it.
   */
  def getIntern(id: Int): Option[Knol] = {
    val conn = getConnect()

    conn match {
      case Some(conn) =>
        try {
          val query = "select * from KNOL where id=" + id + ";"
          val ps = conn.createStatement();
          val set = ps.executeQuery(query);

          set.next()
          val kid = set.getInt("id")
          val kname = set.getString("name")
          val kemail = set.getString("email")
          val kmobile = set.getString("mobile")

          //   logger.debug(Knol(kmobile, kname, kemail, kid))
          Some(Knol(kname, kemail, kmobile, kid))
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
   * getIntern method retrieves all the rows of table KNOL.
   */
  import scala.collection.mutable.MutableList
  def getInternList(): Option[MutableList[Knol]] = {
    val conn = getConnect()
    val knol_list = MutableList[Knol]()
    conn match {
      case Some(conn) =>
        try{
        val query = "select * from KNOL;"
        val ps = conn.createStatement();
        val set = ps.executeQuery(query);

        while (set.next()) {
          val kid = set.getInt("id")
          val kname = set.getString("name")
          val kemail = set.getString("email")
          val kmobile = set.getString("mobile")
          knol_list += (Knol(kmobile, kname, kemail, kid))
        }
        Some(knol_list)
        } catch {
          case ex: Exception => None
        } finally {
          conn.close()
        }
      case None =>
        None
    }
  }

}
