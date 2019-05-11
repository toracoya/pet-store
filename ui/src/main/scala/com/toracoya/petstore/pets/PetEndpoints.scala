package com.toracoya.petstore.pets

import cats.data.Validated.{Invalid, Valid}
import cats.effect.Effect
import cats.implicits._
import com.toracoya.petstore.error.json.ErrorsJson
import com.toracoya.petstore.pagination.PaginationValidator
import com.toracoya.petstore.pet.PetService
import com.toracoya.petstore.pets.json.PetsJson
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

import scala.language.higherKinds

class PetEndpoints[F[_]: Effect] extends Http4sDsl[F] {

  import com.toracoya.petstore.pagination.Pagination._

  def routes(service: PetService[F]): HttpRoutes[F] = list(service)

  private def list(service: PetService[F]): HttpRoutes[F] =
    HttpRoutes.of[F] {
      case GET -> Root / "pets" :? PageMatcher(maybePage) :? PageSizeMatcher(maybePageSize) =>
        val page = maybePage.getOrElse(DefaultPage)
        val pageSize = maybePageSize.getOrElse(DefaultPageSize)

        PaginationValidator.validate(page, pageSize) match {
          case Valid(pagination) =>
            val (from, until) = pagination.range
            for {
              retrieved <- service.list(from, until)
              response <- Ok(PetsJson.from(retrieved).asJson)
            } yield response
          case Invalid(errors) =>
            BadRequest(ErrorsJson.from(errors).asJson)
        }
    }

}

object PetEndpoints {

  def apply[F[_]: Effect](service: PetService[F]): HttpRoutes[F] = new PetEndpoints[F].routes(service)
}
