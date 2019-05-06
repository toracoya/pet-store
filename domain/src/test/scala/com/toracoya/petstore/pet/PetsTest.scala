package com.toracoya.petstore.pet

import org.scalatest.WordSpec

class PetsTest extends WordSpec {

  "#slice" when {
    "empty" should {
      "return empty" in {
        assert(Pets.empty.slice(0, 100) == Pets.empty)
        assert(Pets.empty.slice(1, 100) == Pets.empty)
      }
    }

    "have more than or equal to one pet" should {
      "return sliced" in {
        val pets = Pets(pet1, pet2, pet3)

        assert(pets.slice(0, 100) == pets)
        assert(pets.slice(0, 0) == Pets.empty)
        assert(pets.slice(0, 1) == Pets(pet1))
        assert(pets.slice(0, 2) == Pets(pet1, pet2))
        assert(pets.slice(1, 3) == Pets(pet2, pet3))
        assert(pets.slice(3, 10) == Pets.empty)
      }
    }
  }
}
