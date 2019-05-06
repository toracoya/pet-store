ThisBuild / organization := "com.toracoya"
ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version      := "0.1.0-SNAPSHOT"

ThisBuild / scalafmtOnCompile := true

lazy val petStore = project
  .in(file("."))
  .aggregate(domain)
  .dependsOn(domain)

lazy val domain = project
  .in(file("domain"))
  .settings(moduleName := "domain", name := "Domain")
