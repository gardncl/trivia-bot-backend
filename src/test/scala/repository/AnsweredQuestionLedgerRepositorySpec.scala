package repository

import java.util.Date

import cats.effect.IO
import doobie.specs2._
import doobie.util.transactor.Transactor
import model.AnsweredQuestionInsert
import org.specs2.mutable.Specification

class AnsweredQuestionLedgerRepositorySpec extends Specification with IOChecker {

  override def transactor = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", "jdbc:postgresql:world", "postgres", ""
  )

  private val repository = new AnsweredQuestionLedgerRepository(transactor)

  val entry = AnsweredQuestionInsert(1, "Calvin Coolidge", false, new Date())
  check(repository.insert(entry))
}
