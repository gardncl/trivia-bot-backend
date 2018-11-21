package route

import cats.effect.{Effect, IO}
import cats.implicits._
import config.Config
import db.Database
import fs2.{Stream, StreamApp}
import io.circe.generic.auto._
import io.circe.syntax._
import model._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.{HttpService, Response, Status, UrlForm}
import repository.QuestionRepository
import routes.QuestionsRoute
import service.QuestionService

import scala.concurrent.ExecutionContext.Implicits.global

case class Message(text: String)

object Server extends StreamApp[IO] with Http4sDsl[IO] {

  private final val QUESTIONS = "questions"

  def service[F[_]](questionRepo: QuestionRepository[F])(implicit F: Effect[F]) = HttpService[F] {

    case GET -> Root / QUESTIONS / questionid =>
      questionRepo.getQuestion(questionid).flatMap {
        case Some(question) => Response(status = Status.Ok).withBody(question.asJson)
        case None => F.pure(Response(status = Status.NotFound))
      }

    case req@POST -> Root / QUESTIONS =>
      req.decode[UrlForm] { form =>
        form.values.get("text") match {
          case Some(seq) => {
            val question: Question = Question(seq.mkString)
            questionRepo.addQuestion(question)
              .flatMap(uuid => Response(status = Status.Created)
                .withBody(Message(uuid).asJson))
          }
          case None => F.pure(Response(status = Status.BadRequest))
        }
      }


    case req@POST -> Root / "example" =>
      req.decode[UrlForm](form => Response(status = Status.Ok).withBody(Message(form.values.toString).asJson))

  }

  override def stream(args: List[String], requestShutdown: IO[Unit]) = {
    val ip = Option(System.getenv("OPENSHIFT_DIY_IP")).getOrElse("0.0.0.0")
    val port = (Option(System.getenv("PORT")) orElse
      Option(System.getenv("HTTP_PORT")))
      .map(_.toInt)
      .getOrElse(8080)

    for {
      config <- Stream.eval(Config.load())
      transactor <- Stream.eval(Database.transactor(config.database))
      _ <- Stream.eval(Database.initialize(transactor))
      exitCode <- BlazeBuilder[IO]
        .bindHttp(port, ip)
        .mountService(new QuestionsRoute(new QuestionService(transactor)).foo, "/")
        .serve

    } yield exitCode
  }
}
