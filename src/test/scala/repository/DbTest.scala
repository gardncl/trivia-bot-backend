package repository

import cats.effect.IO
import doobie.specs2.IOChecker
import doobie.util.transactor.Transactor
import doobie.util.update
import org.flywaydb.core.Flyway
import org.specs2.mutable.Specification

class DbTest extends Specification with IOChecker {

  lazy val url = "jdbc:postgresql://localhost:5432/travis_ci_test"
  lazy val user = "postgres"
  lazy val pass = ""

  override def transactor = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    url,
    user,
    pass
  )

  def runMigrations(): Unit = {
    lazy val flyway = new Flyway
    flyway.setDataSource(url, user, pass)
    flyway.setLocations("classpath:db/migration")
    flyway.migrate()
    ()
  }

  runMigrations()

  val megalist: List[update.Update0] =
    List(AnsweredQuestionLedgerRepositorySpec)
      .flatMap(_.runTests(transactor))

  megalist.map(check)

}
