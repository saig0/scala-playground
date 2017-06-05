import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "github.saig0",
      scalaVersion := "2.12.1",
      version      := "1.0.0-SNAPSHOT"
    )),
    name := "scala-playground",
    libraryDependencies += scalaTest % Test
  )
