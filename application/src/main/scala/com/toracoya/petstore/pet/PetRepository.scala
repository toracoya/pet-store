package com.toracoya.petstore.pet

trait PetRepository[F[_]] {

  def list(from: Int, until: Int): F[Pets]
}
