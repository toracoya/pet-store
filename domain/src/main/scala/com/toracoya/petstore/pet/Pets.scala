package com.toracoya.petstore.pet

case class Pets(toList: List[Pet]) extends AnyVal {

  def count: Int = toList.size

  def init: Pets =
    if (isEmpty) {
      this
    } else {
      Pets(toList.init)
    }

  def isEmpty: Boolean = toList.isEmpty

  def slice(from: Int, until: Int): Pets = Pets(toList.slice(from, until))
}

object Pets {

  def apply(pets: Pet*): Pets = Pets(pets.toList)

  val empty: Pets = Pets(List.empty)
}
