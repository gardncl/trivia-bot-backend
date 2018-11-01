package repository


import cats.effect.IO
import doobie.util.transactor.Transactor
import fs2.Stream
import doobie._
import doobie.implicits._
import model.QuestionAnswer

class QuestionAnswerRepository(transactor: Transactor[IO]) {

  def getQuestionAnswers: Stream[IO, QuestionAnswer] = {
    sql"SELECT * FROM question_answer".query[QuestionAnswer].stream.transact(transactor)
  }

  def getQuestionAnswer(id: String): IO[Either[RuntimeException, QuestionAnswer]] = {
    sql"SELECT * FROM question_answer WHERE id = $id".query[QuestionAnswer].option.transact(transactor).map {
      case Some(todo) => Right(todo)
      case None => Left(new RuntimeException)
    }
  }

  def createQuestionAnswer(questionAnswer: QuestionAnswer): IO[QuestionAnswer] = {
    sql"INSERT INTO question_answer (question, answer) VALUES (${questionAnswer.question}, ${questionAnswer.answer})".update.withUniqueGeneratedKeys[Int]("id").map(_.toString).transact(transactor).map {
      id => questionAnswer.copy(id = id)
    }
  }

}
