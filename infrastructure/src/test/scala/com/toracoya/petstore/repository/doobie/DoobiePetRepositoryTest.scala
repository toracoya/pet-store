package com.toracoya.petstore.repository.doobie

import com.toracoya.petstore.pet.{Pet, PetId, PetName, Pets}

class DoobiePetRepositoryTest extends DoobieSpec {

  "#list" should {
    "return pets" in withTransactor { transactor =>
      val repository = DoobiePetRepository(transactor)
      val expects = allPets

      assert(repository.list(0, 20).unsafeRunSync() == expects)

      assert(repository.list(0, 2).unsafeRunSync() == expects.slice(0, 2))
      assert(repository.list(1, 2).unsafeRunSync() == expects.slice(1, 2))
    }
  }

  private val allPets = Pets(
    Pet(PetId(1L), PetName("Bailey")),
    Pet(PetId(2L), PetName("Bella")),
    Pet(PetId(3L), PetName("Max"))
  )
}
