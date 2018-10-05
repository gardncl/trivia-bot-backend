import sbt._

object Dependencies {

  object Version {
    val Http4sVersion = "0.18.19"
  }

  val deps = Seq(
    http4s
  )

  val http4s = Seq(
    "org.http4s" %% "http4s-blaze-server" % Version.Http4sVersion,
    "org.http4s" %% "http4s-circe" % Version.Http4sVersion,
    "org.http4s" %% "http4s-dsl" % Version.Http4sVersion
  )

}
