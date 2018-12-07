package repository

import cats.effect.IO
import doobie.implicits._
import doobie.util.transactor.Transactor
import doobie.util.update
import model.AnsweredQuestionInsert

class AnsweredQuestionLedgerRepository(transactor: Transactor[IO]) {

  private[repository] def insert(entry: AnsweredQuestionInsert): update.Update0 =
    sql"insert into answered_question_ledger (asked_question_id, answer, correct, responded_at) values (${entry.askedQuestionId}, ${entry.guess}, ${entry.correct}, ${entry.respondedAt});"
      .update

  def insertAndReturnId(entry: AnsweredQuestionInsert): IO[Int] =
    insert(entry).withUniqueGeneratedKeys[Int]("id").transact(transactor)

}
