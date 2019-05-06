package com.toracoya.petstore.pet

import cats._

import scala.language.higherKinds

class PetService[F[_]: Applicative] {

  import cats.syntax.all._

  def list(from: Int, until: Int): F[Pets] = pets.slice(from, until).pure[F]

  private val pets: Pets = Pets(
    Pet(PetId(1L), PetName("Max")),
    Pet(PetId(2L), PetName("Bella")),
    Pet(PetId(3L), PetName("Lucy"))
  )
}

object PetService {

  def apply[F[_]: Applicative]: PetService[F] = new PetService[F]
}
