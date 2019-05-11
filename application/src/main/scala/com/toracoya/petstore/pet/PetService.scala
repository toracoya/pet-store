package com.toracoya.petstore.pet

import scala.language.higherKinds

class PetService[F[_]](repository: PetRepository[F]) {

  def list(from: Int, until: Int): F[Pets] = repository.list(from, until)
}

object PetService {

  def apply[F[_]](repository: PetRepository[F]): PetService[F] = new PetService[F](repository)
}
