package com.toracoya.petstore.pets

import cats.effect.Effect
import cats.implicits._
import com.toracoya.petstore.pet.{Pet, PetId, PetName}
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

import scala.language.higherKinds

class Endpoints[F[_]: Effect] extends Http4sDsl[F] {

  def endpoints: HttpRoutes[F] = list

  private def list: HttpRoutes[F] =
    HttpRoutes.of[F] {
      case GET -> Root / "pets" =>
        for {
          retrieved <- pets
          response <- Ok(retrieved.asJson)
        } yield response
    }

  private def pets: F[List[Pet]] =
    List(
      Pet(PetId(1L), PetName("Max")),
      Pet(PetId(2L), PetName("Bella")),
      Pet(PetId(3L), PetName("Lucy"))
    ).pure[F]

}

object Endpoints {

  def apply[F[_]: Effect]: HttpRoutes[F] = new Endpoints[F].endpoints
}
