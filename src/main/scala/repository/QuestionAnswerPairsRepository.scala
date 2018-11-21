package repository


import cats.effect.IO
import doobie.util.transactor.Transactor
import fs2.Stream
import doobie._
import doobie.implicits._
import model.QuestionAnswerPair

class QuestionAnswerPairsRepository(transactor: Transactor[IO]) {

  def getQuestionAnswers: Stream[IO, QuestionAnswerPair] = {
    sql"SELECT * FROM question_answer_pairs".query[QuestionAnswerPair].stream.transact(transactor)
  }

  def getQuestionAnswer(id: String): IO[Either[RuntimeException, QuestionAnswerPair]] = {
    sql"SELECT * FROM question_answer_pairs WHERE id = $id".query[QuestionAnswerPair].option.transact(transactor).map {
      case Some(todo) => Right(todo)
      case None => Left(new RuntimeException)
    }
  }

  def createTodo(questionAnswer: QuestionAnswerPair): IO[QuestionAnswerPair] = {
    sql"INSERT INTO question_answer_pairs (question, answer) VALUES (${questionAnswer.question}, ${questionAnswer.answer})".update.withUniqueGeneratedKeys[Int]("id").map(_.toString).transact(transactor).map {
      id => questionAnswer.copy(id = id)
    }
  }

}
