package repository

import cats.effect.IO
import doobie.implicits._
import doobie.util.transactor.Transactor
import model.AnsweredQuestionInsert

class AnsweredQuestionLedgerRepository(transactor: Transactor[IO]) {

  def insert(entry: AnsweredQuestionInsert): IO[Int] =
    sql"insert into answered_question_ledger (asked_question_id, answer, correct, responded_at) values (${entry.askedQuestionId}, ${entry.guess}, ${entry.correct}, ${entry.respondedAt});"
      .update.withUniqueGeneratedKeys[Int]("id")
      .transact(transactor)

}
