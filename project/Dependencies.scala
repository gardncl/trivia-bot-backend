import sbt._

object Dependencies {

  object Version {
    val Http4sVersion = "0.18.19"
    val CirceVersion = "0.9.1"
    val LogbackVersion = "1.2.3"
    val Doobie = "0.5.2"
    val PureConfig = "0.9.1"
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
    "org.scalatest" %% "scalatest" % "3.0.5",
    "org.tpolecat"   %% "doobie-specs2" % Version.Doobie,
  ).map(_ % Test)

  lazy val logging = Seq(
    "ch.qos.logback"  %  "logback-classic" % Version.LogbackVersion
  )

  lazy val swagger = Seq(
    "org.webjars" % "swagger-ui" % "3.2.2"
  )

  lazy val database = Seq(
    "org.tpolecat"   %% "doobie-core"     % Version.Doobie,
    "org.tpolecat"   %% "doobie-hikari"   % Version.Doobie,
    "org.tpolecat"   %% "doobie-postgres" % Version.Doobie,
    "org.postgresql" % "postgresql"       % "42.1.4",
    "org.flywaydb"   % "flyway-core"      % "4.2.0"
  )

  lazy val config = Seq(
    "com.github.pureconfig" %% "pureconfig" % Version.PureConfig
  )

  val deps: Seq[ModuleID] = http4s ++ circe ++ test ++ logging ++ swagger ++ database ++ config


}
