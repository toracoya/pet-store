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

val catsVersion = "1.6.0"
val catsEffectVersion = "1.3.0"
val http4sVersion = "0.20.0"
val circeVersion = "0.11.1"
val circeConfigVersion = "0.6.1"
val doobieVersion = "0.6.0"
val logbackVersion = "1.2.3"
val scalaTestVersion = "3.0.5"
val mockitoScalaVersion = "1.4.0-beta.8"

lazy val commonDependencies = Seq(
  "org.typelevel" %% "cats-core"   % catsVersion,
  "org.typelevel" %% "cats-effect" % catsEffectVersion,
  "ch.qos.logback" %  "logback-classic" % logbackVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  "org.mockito" %% "mockito-scala" % mockitoScalaVersion % Test,
  "org.mockito" %% "mockito-scala-scalatest" % mockitoScalaVersion % Test
)

lazy val databaseDependencies = Seq(
  "org.tpolecat" %% "doobie-core"      % doobieVersion,
  "org.tpolecat" %% "doobie-hikari"    % doobieVersion,
  "org.tpolecat" %% "doobie-postgres"  % doobieVersion,
  "org.tpolecat" %% "doobie-scalatest" % doobieVersion % Test
)

lazy val circeDependencies = Seq(
  "io.circe" %% "circe-generic"        % circeVersion,
  "io.circe" %% "circe-literal"        % circeVersion,
  "io.circe" %% "circe-generic-extras" % circeVersion,
  "io.circe" %% "circe-parser"         % circeVersion,
  "io.circe" %% "circe-config"         % circeConfigVersion
)

lazy val petStore = project
  .in(file("."))
  .aggregate(domain, application, infrastructure, userAPI)
  .dependsOn(domain, application, infrastructure, userAPI)

lazy val domain = project
  .in(file("domain"))
  .settings(moduleName := "domain", name := "Domain")
  .settings(libraryDependencies := commonDependencies)

lazy val application = project
  .in(file("application"))
  .settings(moduleName := "application", name := "Application")
  .settings(libraryDependencies := commonDependencies)
  .dependsOn(domain)

lazy val infrastructure = project
  .in(file("infrastructure"))
  .settings(moduleName := "infrastructure", name := "Infrastructure")
  .settings(libraryDependencies := commonDependencies ++ databaseDependencies ++ circeDependencies)
  .dependsOn(domain, application)

lazy val userAPI = project
  .in(file("user-api"))
  .settings(moduleName := "user-api", name := "User API")
  .settings(libraryDependencies := commonDependencies ++ circeDependencies ++ Seq(
    "org.http4s" %% "http4s-dsl"          % http4sVersion,
    "org.http4s" %% "http4s-blaze-server" % http4sVersion,
    "org.http4s" %% "http4s-circe"        % http4sVersion
  ))
  .settings(Seq(
    fork in run := true,
    cancelable in Global := true
  ))
  .dependsOn(domain, application, infrastructure)
