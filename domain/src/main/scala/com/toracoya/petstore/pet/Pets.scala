package com.toracoya.petstore.pet

case class Pets(toList: List[Pet]) extends AnyVal {

  def slice(from: Int, until: Int): Pets = Pets(toList.slice(from, until))
}

object Pets {

  def apply(pets: Pet*): Pets = Pets(pets.toList)

  val empty: Pets = Pets(List.empty)
}
