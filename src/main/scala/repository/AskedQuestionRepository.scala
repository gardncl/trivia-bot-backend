package repository

import cats.effect.IO
import model.AskedQuestion
import doobie.implicits._
import doobie.util.transactor.Transactor

class AskedQuestionRepository(transactor: Transactor[IO]) {

  def getMostRecentQuestionByUser(userId: Int): IO[Option[AskedQuestion]] = {
    sql"""select * from asked_question_ledger
         |where user_id = $userId
         |order by asked_at desc
         |limit 1;"""
      .query[AskedQuestion]
      .option
      .transact(transactor)
  }

  def getAnswerToMostRecentQuestionAskedToUser(userId: Int): IO[Option[String]] = {
    sql"""
         |select * from asked_question_ledger as l
         |join question_answer_pairs as p on l.question_id = p.id
         |where l.user_id = $userId
         |order by l.asked_at limit 1;
       """
      .query[String]
      .option
      .transact(transactor)
  }

}
