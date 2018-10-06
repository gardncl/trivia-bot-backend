import Utilities._
import cats.effect.IO
import model.{Question, QuestionWithId}
import org.http4s._
import org.http4s.implicits._
import org.scalatest.FunSpec
import repository.QuestionRepository
import route.Server

import scala.collection.mutable.ListBuffer

class ServerSpec extends FunSpec {

  val id = "123"
  val question = Question("What is the wingspan of a 747?")
  val qWithId = QuestionWithId(id, question)
  val questionRepository = QuestionRepository[IO](ListBuffer(qWithId))

  implicit val entityDecoder = EntityDecoder[IO, QuestionWithId]

  def testService: HttpService[IO] = Server.service[IO](questionRepository)

  describe("GET ~> /questions") {
    it("should return 200 when the question exists") {
      val getQuestion = Request[IO](Method.GET, Uri.uri("/questions/123"))
      val response = testService.orNotFound(getQuestion)
      assert(check[QuestionWithId](response, Status.Ok, Some(qWithId)))
    }
    it("should return 404 when the question does not exist") {
      val getQuestion = Request[IO](Method.GET, Uri.uri("/questions/100"))
      val response = testService.orNotFound(getQuestion)
      assert(check[QuestionWithId](response, Status.NotFound, None))
    }
  }



}
