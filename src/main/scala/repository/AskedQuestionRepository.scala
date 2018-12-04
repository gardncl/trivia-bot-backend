package repository

import cats.effect.IO
import model.AskedQuestion
import doobie.implicits._
import doobie.util.transactor.Transactor

class AskedQuestionRepository(transactor: Transactor[IO]) {

  def getMostRecentQuestionByUser(userId: String): IO[Option[AskedQuestion]] = {
    sql"""select * from asked_question_ledger
         |where user_id = $userId
         |order by asked_at desc
         |limit 1;"""
      .query[AskedQuestion]
      .option
      .transact(transactor)
  }

  def getAnswerToMostRecentQuestionAskedToUser(userId: String): IO[Option[(String, Int)]] = {
    sql" select p.answer, l.id from asked_question_ledger as l join question_answer_pairs as p on l.question_id = p.id where l.user_id = $userId order by l.asked_at desc limit 1; "
      .query[(String, Int)]
      .option
      .transact(transactor)
  }

}
