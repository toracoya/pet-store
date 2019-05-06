ThisBuild / organization := "com.toracoya"
ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version      := "0.1.0-SNAPSHOT"

ThisBuild / scalafmtOnCompile := true

ThisBuild / scalacOptions ++= Seq(
  "-encoding",
  "UTF-8",
  "-feature",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture",
  "-Yno-adapted-args",
  "-Xfatal-warnings",
  "-deprecation",
  "-Ypartial-unification")

val catsVersion = "1.3.0"
val http4sVersion = "0.20.0"
val circeVersion = "0.11.1"

lazy val petStore = project
  .in(file("."))
  .aggregate(domain, ui)
  .dependsOn(domain, ui)

lazy val domain = project
  .in(file("domain"))
  .settings(moduleName := "domain", name := "Domain")

lazy val ui = project
  .in(file("ui"))
  .settings(moduleName := "ui", name := "User interface")
  .settings(libraryDependencies := Seq(
    "org.typelevel" %% "cats-core"   % catsVersion,
    "org.typelevel" %% "cats-effect" % catsVersion,
    "org.http4s" %% "http4s-dsl"          % http4sVersion,
    "org.http4s" %% "http4s-blaze-server" % http4sVersion,
    "org.http4s" %% "http4s-circe"        % http4sVersion,
    "io.circe" %% "circe-generic"        % circeVersion,
    "io.circe" %% "circe-literal"        % circeVersion,
    "io.circe" %% "circe-generic-extras" % circeVersion,
    "io.circe" %% "circe-parser"         % circeVersion
  ))
  .settings(Seq(
    fork in run := true,
    cancelable in Global := true
  ))
  .dependsOn(domain)
