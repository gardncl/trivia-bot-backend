import sbt._

object Dependencies {

  object Version {
    val Http4sVersion = "0.18.19"
    val CirceVersion = "0.9.1"
    val LogbackVersion = "1.2.3"
  }

  lazy val http4s: Seq[ModuleID] = Seq(
    "org.http4s" %% "http4s-blaze-server" % Version.Http4sVersion,
    "org.http4s" %% "http4s-blaze-client" % Version.Http4sVersion,
    "org.http4s" %% "http4s-circe" % Version.Http4sVersion,
    "org.http4s" %% "http4s-dsl" % Version.Http4sVersion
  )

  lazy val circe = Seq(
    "io.circe" %% "circe-core" % Version.CirceVersion,
    "io.circe" %% "circe-generic" % Version.CirceVersion,
    "io.circe" %% "circe-literal" % Version.CirceVersion
  )

  lazy val test = Seq(
    "org.scalatest" %% "scalatest" % "3.0.5"
  ).map(_ % Test)

  lazy val logging = Seq(
    "ch.qos.logback"  %  "logback-classic" % Version.LogbackVersion
  )

  val deps: Seq[ModuleID] = http4s ++ circe ++ test ++ logging


}
