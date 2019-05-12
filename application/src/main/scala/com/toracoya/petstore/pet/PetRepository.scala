package com.toracoya.petstore.pet

trait PetRepository[F[_]] {

  def list(from: Int, until: Int): F[Pets]

  def getBy(id: PetId): F[Option[Pet]]
}
