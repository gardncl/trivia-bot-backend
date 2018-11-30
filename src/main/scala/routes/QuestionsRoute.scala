package routes

import cats.effect.{Effect, IO}
import io.circe.generic.auto._
import io.circe.syntax._
import model._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.{HttpService, Response, Status, UrlForm}
import route.Message
import service.{AnswerQuestionService, QuestionService}

class QuestionsRoute(questionService: QuestionService, answerQuestionService: AnswerQuestionService) extends Http4sDsl[IO] {

  private final val QUESTIONS = "questions"
  private final val GUESS = "guess"

  def foo(implicit F: Effect[IO]): HttpService[IO] = HttpService[IO] {
    case req@POST -> Root / QUESTIONS =>
      req.decode[UrlForm] { form =>
        form.values.get("text") match {
          case Some(seq) => {
            val question = seq.mkString
            val foo = QuestionAnswerPair(question, "John Muir", "anything")
            questionService.addQuestion(foo)
              .flatMap(uuid => Response(status = Status.Created)
                .withBody(Message(uuid.id).asJson))
          }
          case None => F.pure(Response(status = Status.BadRequest))
        }
      }

    case req@POST -> Root / QUESTIONS / GUESS =>
      req.decode[UrlForm] { form =>
        form.values.get("text") match {
          case Some(seq) => {
            val userId = form.values.get("user_id")
            val guess = seq.mkString
            answerQuestionService.handleGuess(userId.get.mkString, guess)
              .flatMap(correct => Response(status = Status.Ok)
                .withBody(Message(correct.toString).asJson))
          }
          case None => F.pure(Response(status = Status.BadRequest))
        }
      }
  }

}