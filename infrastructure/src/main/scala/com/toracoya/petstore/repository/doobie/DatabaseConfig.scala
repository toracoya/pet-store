package com.toracoya.petstore.repository.doobie

import cats.effect.{Async, ContextShift, Resource}
import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts
import io.circe.Decoder
import io.circe.generic.semiauto._

case class ConnectionConfig(poolSize: Int)

case class DatabaseConfig(url: String, driver: String, user: String, password: String, connection: ConnectionConfig) {

  def transactor[F[_]: Async: ContextShift]: Resource[F, HikariTransactor[F]] =
    for {
      connectEC <- ExecutionContexts.fixedThreadPool[F](connection.poolSize)
      transactEC <- ExecutionContexts.cachedThreadPool[F]
      transactor <- HikariTransactor.newHikariTransactor[F](driver, url, user, password, connectEC, transactEC)
    } yield transactor
}

object DatabaseConfig {

  implicit val connectionDecoder: Decoder[ConnectionConfig] = deriveDecoder

  implicit val dataBaseDecoder: Decoder[DatabaseConfig] = deriveDecoder
}
