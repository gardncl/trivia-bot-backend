package repository

import cats.effect.IO
import model.AnsweredQuestion

class AnsweredQuestionLedgerRepository {

  def insert(entry: AnsweredQuestion): IO[Int] = ???

}
