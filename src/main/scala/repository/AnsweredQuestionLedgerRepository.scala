package repository

import doobie.implicits._
import model.AnsweredQuestionInsert

class AnsweredQuestionLedgerRepository {

  def insert(entry: AnsweredQuestionInsert) =
    sql"""insert into answered_question_ledger
         |(asked_question_id, answer, correct, responded_at)
         |values
         |(${entry.askedQuestionId},
         |${entry.guess},
         |${entry.correct},
         |${entry.respondedAt});""".update

}
