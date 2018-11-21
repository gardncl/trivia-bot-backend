package service

import cats.effect.IO
import doobie.util.transactor.Transactor
import model.QuestionAnswerPair
import repository.QuestionAnswerPairsRepository

class QuestionService(transactor: Transactor[IO]) {

  private final val questionAnswerRepo = new QuestionAnswerPairsRepository(transactor)

  def addQuestion(qa: QuestionAnswerPair): IO[QuestionAnswerPair] = {
    questionAnswerRepo.addQuestionAnswer(qa)
  }

}
