package com.knol.core
import scala.collection.mutable.MutableList
trait KnolSessionRepo {

  def getDetail(): Option[MutableList[KnolSession]]
}

case class KnolSession(id: Int, name: String, email: String, mobile: String, sid: Int, topic: String, date: String, knol_id: Int)

