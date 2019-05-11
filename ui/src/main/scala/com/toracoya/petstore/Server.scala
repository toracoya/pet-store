package com.toracoya.petstore

import cats.effect._
import cats.implicits._
import com.toracoya.petstore.config.PetStoreConfig
import com.toracoya.petstore.pet.PetService
import com.toracoya.petstore.repository.doobie.DoobiePetRepository
import io.circe.config.parser
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder

import scala.language.higherKinds

object Server extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    buildServer
      .use { _ =>
        IO.never
      }
      .as(ExitCode.Success)

  private def buildServer[F[_]: ContextShift: ConcurrentEffect: Timer] =
    for {
      httpApp <- buildHttpApp[F]
      server <- BlazeServerBuilder[F]
        .bindHttp(8080, "localhost")
        .withHttpApp(httpApp)
        .resource
    } yield server

  private def buildHttpApp[F[_]: ContextShift: ConcurrentEffect: Timer] =
    for {
      config <- Resource.liftF(parser.decodePathF[F, PetStoreConfig]("petstore"))
      transactor <- config.db.transactor
      petRepository = DoobiePetRepository[F](transactor)
      endpoints = pets.PetEndpoints.apply[F](PetService.apply[F](petRepository))
    } yield Router("/" -> endpoints).orNotFound

}
