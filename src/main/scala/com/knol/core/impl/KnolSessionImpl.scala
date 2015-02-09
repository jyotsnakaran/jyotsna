package com.knol.core.impl
import com.knol.core._
import com.knol.db.connection.DBConnection
import scala.collection.mutable.MutableList
class KnolSessionImpl extends KnolSessionRepo with DBConnection {
  /**
   * this method performs join operation on table knol and knolx and
   * returns
   */
  def getDetail(): Option[MutableList[KnolSession]] = {
    val conn = getConnect()
    val session_list = MutableList[KnolSession]()
    conn match {
      case Some(conn) =>
        try {
          val query = "select * from KNOL JOIN KNOLX ON KNOL.id=KNOLX.knol_id"
          val ps = conn.createStatement();
          val set = ps.executeQuery(query);

          while (set.next()) {
            val kid = set.getInt("KNOL.id")
            val kname = set.getString("KNOL.name")
            val kemail = set.getString("KNOL.email")
            val kmobile = set.getString("KNOL.mobile")
            val session_id = set.getInt("KNOLX.id")
            val session_topic = set.getString("KNOLX.topic")
            val session_date = set.getString("KNOLX.sdate")
            val session_kid = set.getInt("KNOLX.knol_id")

            session_list += KnolSession(kid, kname, kemail, kmobile, session_id, session_topic, session_date, session_kid)
          }

          Some(session_list)
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
}
