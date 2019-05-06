package com.toracoya.petstore

import cats.effect._
import cats.implicits._
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder

import scala.language.higherKinds

object Server extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(httpApp)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)

  private def httpApp[F[_]: ContextShift: ConcurrentEffect: Timer] = Router("/" -> services[F]).orNotFound

  private def services[F[_]: ContextShift: ConcurrentEffect: Timer] = pets.Endpoints.apply[F]
}
