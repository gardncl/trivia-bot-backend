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

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "tpolecat" at "http://dl.bintray.com/tpolecat/maven",
  "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
)

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)