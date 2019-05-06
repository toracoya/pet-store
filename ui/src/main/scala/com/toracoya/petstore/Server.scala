package com.toracoya.petstore

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.impl.Root
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

object Server extends IOApp {

  private val helloWorld = HttpRoutes
    .of[IO] {
      case GET -> Root / "hello" / name =>
        Ok(s"Hello, $name.")
    }
    .orNotFound

  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(helloWorld)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
