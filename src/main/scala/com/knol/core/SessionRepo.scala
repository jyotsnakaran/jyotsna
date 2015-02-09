package com.knol.core

import scala.collection.mutable.MutableList

trait SessionRepo {

  def create(session: Session): Option[Int]
  def update(session: Session): Option[Int]
  def delete(id: Int): Option[Int]
  def getById(id: Int): Option[Session]
  def getList(): Option[MutableList[Session]]
}

case class Session(topic: String, date: String, knol_id: Int, id: Int=0)
