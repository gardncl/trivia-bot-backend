package route

import cats.effect.{Effect, IO}
import cats.implicits._
import fs2.{Stream, StreamApp}
import io.circe.generic.auto._
import io.circe.syntax._
import model._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.{HttpService, Response, Status}
import repository.QuestionRepository

import scala.concurrent.ExecutionContext.Implicits.global

object Server extends StreamApp[IO] with Http4sDsl[IO] {

  private final val QUESTIONS = "questions"

  def service[F[_]](questionRepo: QuestionRepository[F])(implicit F: Effect[F]) = HttpService[F] {

    case GET -> Root / QUESTIONS / questionid =>
      questionRepo.getQuestion(questionid).flatMap {
        case Some(question) => Response(status = Status.Ok).withBody(question.asJson)
        case None => F.pure(Response(status = Status.NotFound))
      }

    case req@POST -> Root / QUESTIONS =>
      req.decodeJson[Question]
        .flatMap(question => questionRepo.addQuestion(question))
        .flatMap(question => Response(status = Status.Created).withBody(question.asJson))
  }

  override def stream(args: List[String], requestShutdown: IO[Unit])
  = Stream.eval(QuestionRepository.empty[IO]).flatMap { questionRepo =>
    BlazeBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .mountService(service(questionRepo), "/")
      .serve
  }
}
