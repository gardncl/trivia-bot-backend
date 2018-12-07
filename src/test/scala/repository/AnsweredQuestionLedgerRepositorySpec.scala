package repository

import java.util.Date

import cats.effect.IO
import doobie.util.transactor.Transactor
import doobie.util.update
import model.AnsweredQuestionInsert

object AnsweredQuestionLedgerRepositorySpec extends RepositorySpec {


  def runTests(transactor: Transactor[IO]): List[update.Update0] = {
    val repository = new AnsweredQuestionLedgerRepository(transactor)
    val entry = AnsweredQuestionInsert(1, "Calvin Coolidge", false, new Date())
    List(repository.insert(entry))
  }





}
