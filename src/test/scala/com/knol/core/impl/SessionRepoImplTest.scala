package com.knol.core.impl

import org.scalatest.BeforeAndAfter
import com.knol.db.connection.DBConnection
import org.scalatest.FunSuite
import com.knol.core._

class SessionRepoImplTest extends FunSuite with DBConnection with BeforeAndAfter {

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

        val sql_insert = "INSERT INTO KNOLX (topic,sdate,knol_id) values ('java','7-12-2011',1);"

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
  val SRepoImpl = new SessionRepoImpl
  val KnolCreate = Session("asdfghjkl;", "2015-02-28", 1)
  val KnolCreate1 = Session("asdf", "2015-02-28", 6)
  val KnolUpdate = Session("sus", "7890665", 1, 1)
  val KnolUpdate1 = Session("sus", "7890665", 6, 1)

  test("Create Of Session") {
    val result = SRepoImpl.create(KnolCreate)
    assert(result === Some(1))
  }

  test("Exception in Create Of Session") {
    val result = SRepoImpl.create(KnolCreate1)
    assert(result === None)
  }

  test("Update Of Session") {
    val result = SRepoImpl.update(KnolUpdate)
    assert(result === Some(1))
  }

  test("Exception in Update Of Session") {
    val result = SRepoImpl.update(KnolUpdate1)
    assert(result === None)
  }

  test("getById() Of Session") {
    val result = SRepoImpl.getById(1)
    assert(!(result === None))
  }

  test("Exception in getById Of Session") {
    val result = SRepoImpl.getById(3)
    assert(result === None)
  }

  test("getList Of Session") {
    val result = SRepoImpl.getList()
    assert(!(result === None))
  }

  test("delete Of Session") {
    val result = SRepoImpl.delete(1)
    assert(result === Some(1))
  }

  test("Negative delete Of Session") {
    val result = SRepoImpl.delete(4)
    assert(result === None)
  }
}