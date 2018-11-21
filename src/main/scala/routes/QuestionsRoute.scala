package routes

import cats.effect.{Effect, IO}
import doobie.util.transactor.Transactor
import io.circe.generic.auto._
import io.circe.syntax._
import model._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.{HttpService, Response, Status, UrlForm}
import route.Message
import service.QuestionService

class QuestionsRoute(transactor: Transactor[IO])(implicit F: Effect[IO]) extends Http4sDsl[IO] {

  private final val QUESTIONS = "questions"

  private final val questionService = new QuestionService(transactor)

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
  }

}
