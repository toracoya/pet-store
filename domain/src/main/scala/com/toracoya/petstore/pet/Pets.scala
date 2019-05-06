package com.toracoya.petstore.pet

case class Pets(toList: List[Pet]) extends AnyVal

object Pets {

  def apply(pets: Pet*): Pets = Pets(pets.toList)
}
