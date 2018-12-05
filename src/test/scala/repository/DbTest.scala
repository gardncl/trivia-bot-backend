package repository

import cats.effect.IO
import doobie.specs2.IOChecker
import doobie.util.transactor.Transactor
import org.specs2.mutable.Specification

trait DbTest extends Specification with IOChecker {

  override def transactor = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", "jdbc:postgresql://localhost:5432/travis_ci_test", "postgres", ""
  )

}
