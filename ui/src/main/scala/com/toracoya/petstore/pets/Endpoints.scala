package com.toracoya.petstore.pets

import cats.effect.Effect
import cats.implicits._
import com.toracoya.petstore.pet.PetService
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

import scala.language.higherKinds

class Endpoints[F[_]: Effect] extends Http4sDsl[F] {

  def endpoints(service: PetService[F]): HttpRoutes[F] = list(service)

  private def list(service: PetService[F]): HttpRoutes[F] =
    HttpRoutes.of[F] {
      case GET -> Root / "pets" =>
        for {
          retrieved <- service.list
          response <- Ok(retrieved.asJson)
        } yield response
    }

}

object Endpoints {

  def apply[F[_]: Effect](service: PetService[F]): HttpRoutes[F] = new Endpoints[F].endpoints(service)
}
