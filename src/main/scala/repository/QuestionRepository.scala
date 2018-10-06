package repository

import java.util.UUID

import cats.effect.{IO, _}
import cats.implicits._
import model.{Question, QuestionWithId}

import scala.collection.mutable.ListBuffer

final case class QuestionRepository[F[_]](private val questions: ListBuffer[QuestionWithId])(implicit e: Effect[F]) {
  val makeId: F[String] = e.delay {
    UUID.randomUUID().toString
  }

  def getQuestion(id: String): F[Option[QuestionWithId]] =
    e.delay {
      questions.find(_.id == id)
    }

  def addQuestion(question: Question): F[String] =
    for {
      uuid <- makeId
      _ <- e.delay {
        questions += QuestionWithId(uuid, question)
      }
    } yield uuid

}

object QuestionRepository {
  def empty[F[_]](implicit m: Effect[F]): IO[QuestionRepository[F]] = IO {
    new QuestionRepository[F](ListBuffer())
  }
}