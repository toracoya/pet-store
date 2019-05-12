package com.toracoya.petstore.repository.doobie

import cats.Monad
import com.toracoya.petstore.pet._
import com.toracoya.petstore.repository.doobie.SQLPagination._
import doobie.implicits._
import doobie.util.{Get, Meta}
import doobie.util.query.Query0
import doobie.util.transactor.Transactor

private object SQL {

  def selectAll: Query0[Pet] = sql"""
     SELECT id, name
     FROM pets
     ORDER BY id
  """.query

  def select(id: PetId): Query0[Pet] = sql"""
     SELECT id, name
     FROM pets
     WHERE id = $id
  """.query

  implicit val petMeta: Meta[PetId] = Meta[Long].timap(PetId.apply)(_.toLong)

  implicit private val petNameGet: Get[PetName] = Get[String].tmap(name => PetName(name))
}

class DoobiePetRepository[F[_]: Monad](transactor: Transactor[F]) extends PetRepository[F] {

  import SQL._

  override def list(from: Int, until: Int): F[Pets] =
    paginate(until - from, from)(selectAll)
      .to[List]
      .map(Pets.apply)
      .transact(transactor)

  override def getBy(id: PetId): F[Option[Pet]] = select(id).option.transact(transactor)
}

object DoobiePetRepository {

  def apply[F[_]: Monad](transactor: Transactor[F]): DoobiePetRepository[F] =
    new DoobiePetRepository[F](transactor)
}
