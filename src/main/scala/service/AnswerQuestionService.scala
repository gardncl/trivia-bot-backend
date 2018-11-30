package service

import java.util.Date

import cats.effect.IO
import doobie.util.transactor.Transactor
import model.AnsweredQuestionInsert
import repository.{AnsweredQuestionLedgerRepository, AskedQuestionRepository}

class AnswerQuestionService(transactor: Transactor[IO]) {

  private val askedQuestionRepository = new AskedQuestionRepository(transactor)
  private val answeredQuestionLedgerRepository = new AnsweredQuestionLedgerRepository()

  /** takes an answer for a user and inserts the answer */
  def handleGuess(userId: String, guess: String, respondedAt: Date = new Date()): IO[Option[Boolean]] =
    for {
      answerOption <- askedQuestionRepository.getAnswerToMostRecentQuestionAskedToUser(userId)
      _ <- answerOption match {
        case None => IO.pure(1)
        case Some((answer, questionAskedId)) => {
          val correct = answer == guess
          val insert = AnsweredQuestionInsert(questionAskedId, guess, correct, respondedAt)
          IO.pure(answeredQuestionLedgerRepository.insert(insert).run)
        }
      }

    } yield {
      answerOption.map(_._1 == guess)
    }


}
