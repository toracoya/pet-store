package com.toracoya.petstore.repository.doobie

import com.toracoya.petstore.pet.{Pet, PetId, PetName, Pets}

class DoobiePetRepositoryTest extends DoobieSpec {

  "#list" should {
    "return pets" in new Fixture {
      withTransactor { transactor =>
        val repository = DoobiePetRepository(transactor)
        val expects = allPets

        assert(repository.list(0, 20).unsafeRunSync() == expects)

        assert(repository.list(0, 2).unsafeRunSync() == expects.slice(0, 2))
        assert(repository.list(1, 2).unsafeRunSync() == expects.slice(1, 2))
      }
    }
  }

  "#getBy" when {
    "return a pet" in new Fixture {
      withTransactor { transactor =>
        val repository = DoobiePetRepository(transactor)

        assert(repository.getBy(petId1).unsafeRunSync() == Option(pet1))
        assert(repository.getBy(petId2).unsafeRunSync() == Option(pet2))
        assert(repository.getBy(ghostPetId).unsafeRunSync().isEmpty)
      }
    }
  }

  trait Fixture {

    val petId1 = PetId(1L)
    val petId2 = PetId(2L)
    val petId3 = PetId(3L)
    val ghostPetId = PetId(9999L)

    val pet1 = Pet(PetId(1L), PetName("Bailey"))
    val pet2 = Pet(PetId(2L), PetName("Bella"))
    val pet3 = Pet(PetId(3L), PetName("Max"))

    val allPets = Pets(pet1, pet2, pet3)
  }
}
