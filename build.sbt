import Dependencies._

lazy val root = (project in file(".")).settings(
  inThisBuild(
    List(
      scalaVersion := "2.12.7",
      version := "1.0"
    )),
  name := "Trivia-Bot",
  scalacOptions ++= Seq(
    "-Ypartial-unification"
  ),
  libraryDependencies ++= deps
)

resolvers += Resolver.sonatypeRepo("releases")