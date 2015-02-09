package com.knol.core.impl

import java.sql._

import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite

import com.knol.core.Knol
import com.knol.db.connection.DBConnection

class KnolRepoImplTest extends FunSuite with BeforeAndAfter with DBConnection {
  val conn = getConnect
  before {
    conn match {
      case Some(conn) =>
        val stmt = conn.createStatement()

        val sql = "CREATE TABLE KNOL" +
          "(id INT not NULL AUTO_INCREMENT, " +
          " name VARCHAR(20), " +
          " email VARCHAR(20) UNIQUE, " +
          " mobile VARCHAR(13), " +
          " PRIMARY KEY ( id ))"

        val sql_insert = "INSERT INTO KNOL (name,email,mobile) values ('jyotsna','jyo@gmail.com','8588039000') "

        stmt.executeUpdate(sql)
        stmt.executeUpdate(sql_insert)
      case None => None
    }
  }
  after {
    conn match {
      case Some(conn) =>
        val stmt = conn.createStatement()

        val sql = "DROP TABLE KNOL;"

        stmt.executeUpdate(sql)

      case None => None
    }
  }
  val knolRepoImpl = new KnolRepoImpl
  val KnolCreate = Knol("jyoooo", "jl@gmail.com", "498773")
  val KnolCreate1 = Knol("jyoooo", "jyo@gmail.com", "498773")
  val KnolUpdate = Knol("sus", "sushil@gmail.com", "498773", 1)
  val KnolUpdate1 = Knol("sus", "sushil@gmail.com", "498773", 3)
  test("Create() Of Knol") {
    val result = knolRepoImpl.create(KnolCreate)
    assert(result === Some(1))
  }
  
  
  test("Negative Create Of Knol") {
    val result = knolRepoImpl.create(KnolCreate1)
    assert(result === None)
  }
  
  test("Update Of Knol") {
    val result = knolRepoImpl.update(KnolUpdate)

    assert(result === Some(1))

  }

  test("Negative Update Of Knol") {
    val result = knolRepoImpl.update(KnolUpdate1)

    assert(result === None)

  }

  test("getIntern Of Knol") {
    val result = knolRepoImpl.getIntern(1)
    assert(!(result === None))
  }

  test("Negative getIntern Of Knol") {
    val result = knolRepoImpl.getIntern(3)
    assert(result === None)
  }

  test("getInternList Of Knol") {
    val result = knolRepoImpl.getInternList()
    assert(!(result === None))
  }

  test("delete Of Knol") {
    val result = knolRepoImpl.delete(1)
    assert(result === Some(1))
  }
  
    test("Negative delete Of Knol") {
    val result = knolRepoImpl.delete(5)
    assert(result === None)
  }
}