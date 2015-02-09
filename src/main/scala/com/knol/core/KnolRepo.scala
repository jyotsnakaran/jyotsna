package com.knol.core
import scala.collection.mutable.MutableList

trait KnolRepo {
  def create(knol: Knol): Option[Int]
  def update(knol: Knol): Option[Int]
  def delete(id: Int): Option[Int]
  def getIntern(id: Int): Option[Knol]
  def getInternList(): Option[MutableList[Knol]]
}

case class Knol(name: String, email: String, mobile :String, id: Int=0)

