package model

import cats.effect.IO
import io.circe.generic.auto._
import org.http4s.circe._
import org.http4s.{EntityDecoder, EntityEncoder}

case class QuestionWithId(id: String, question: String)

object QuestionWithId {

  def apply(id: String, question: Question): QuestionWithId = new QuestionWithId(id, question.question)

  implicit val encoder: EntityEncoder[IO, QuestionWithId] = jsonEncoderOf[IO, QuestionWithId]
  implicit val decoder: EntityDecoder[IO, QuestionWithId] = jsonOf[IO, QuestionWithId]

}
