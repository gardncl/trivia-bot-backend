package repository

import java.util.Date

import model.AnsweredQuestionInsert

class AnsweredQuestionLedgerRepositorySpec extends DbTest {

  private val repository = new AnsweredQuestionLedgerRepository(transactor)

  runMigrations

  val entry = AnsweredQuestionInsert(1, "Calvin Coolidge", false, new Date())
  check(repository.insert(entry))
}
