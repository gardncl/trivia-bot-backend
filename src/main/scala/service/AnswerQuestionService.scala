package service

import java.util.Date

import cats.effect.IO
import doobie.util.transactor.Transactor
import model.AnsweredQuestionInsert
import repository.{AnsweredQuestionLedgerRepository, AskedQuestionRepository}

class AnswerQuestionService(transactor: Transactor[IO]) {

  private val askedQuestionRepository = new AskedQuestionRepository(transactor)
  private val answeredQuestionLedgerRepository = new AnsweredQuestionLedgerRepository(transactor)

  /** takes an answer for a user and inserts the answer */
  def handleGuess(userId: String, guess: String, respondedAt: Date = new Date()): IO[Option[Boolean]] =
    for {
      answerOption <- askedQuestionRepository.getAnswerToMostRecentQuestionAskedToUser(userId)
      answeredQuestionLedgerEntry = answerOption match {
        case None => None
        case Some((answer, questionAskedId)) => {
          val correct = answer == guess
          Some(AnsweredQuestionInsert(questionAskedId, guess, correct, respondedAt))
        }
      }
      _ <- answeredQuestionLedgerEntry match {
        case None => IO.pure(-1)
        case Some(ledger) =>
          answeredQuestionLedgerRepository.insertAndReturnId(ledger)
      }
    } yield {
      answerOption.map(_._1 == guess)
    }


}
