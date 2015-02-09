package com.knol.core.impl

import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite

import com.knol.db.connection.DBConnection

class KnolSessionImplTest extends FunSuite with DBConnection with BeforeAndAfter{
  
    before {
    val conn = getConnect
    conn match {
      case Some(conn) =>

        val stmt = conn.createStatement()

        val sql_knol = "CREATE TABLE KNOL" +
          "(id INT not NULL AUTO_INCREMENT, " +
          " name VARCHAR(20), " +
          " email VARCHAR(20), " +
          " mobile VARCHAR(13), " +
          " PRIMARY KEY ( id ))"

        val sql_insert_knol = "INSERT INTO KNOL (name,email,mobile) values ('jyotsna','jyo@gmail.com','8588039000') "

        stmt.executeUpdate(sql_knol)
        stmt.executeUpdate(sql_insert_knol)

        val sql = "CREATE TABLE KNOLX" +
          "(id INT AUTO_INCREMENT, " +
          " topic VARCHAR(20), " +
          " sdate VARCHAR(20), " +
          " knol_id INT, " +
          " PRIMARY KEY ( id )," +
          " FOREIGN KEY (knol_id) REFERENCES KNOL(id) ON DELETE CASCADE ON UPDATE CASCADE);"

        //        val sql_alter = "ALTER TABLE KNOLX ADD CONSTRAINT FOREIGN KEY (knol_id) REFERENCES KNOL(id) ON DELETE CASCADE ON UPDATE CASCADE;"

        val sql_insert = "INSERT INTO KNOLX (topic,sdate,knol_id) values ('jyotsna','7-12-2011',1);"

        stmt.executeUpdate(sql)
        //       stmt.executeUpdate(sql_alter) 
        stmt.executeUpdate(sql_insert)
      case None => None
    }

  }
  after {
    val conn = getConnect
    conn match {
      case Some(conn) =>
        val stmt = conn.createStatement()

        val sql = "DROP TABLE KNOLX;"
        val sql_knol = "DROP TABLE KNOL;"
        stmt.executeUpdate(sql)
        stmt.executeUpdate(sql_knol)

      case None => None
    }
  }
  
 val KnolSessImpl = new KnolSessionImpl
  test("select Of KnolSession") {
    val result = KnolSessImpl.getDetail()
    assert(!(result === None))
  }

 test("test connection"){
   val connect=getConnect
 assert(!(connect===None))
 }
 
}