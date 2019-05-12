package com.toracoya.petstore.pet

import scala.language.higherKinds

class PetService[F[_]](repository: PetRepository[F]) {

  def list(from: Int, until: Int): F[Pets] = repository.list(from, until)

  def getBy(id: PetId): F[Option[Pet]] = repository.getBy(id)
}

object PetService {

  def apply[F[_]](repository: PetRepository[F]): PetService[F] = new PetService[F](repository)
}
