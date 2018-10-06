
import cats.effect._
import org.http4s._

object Utilities {

  def check[A](actual: IO[Response[IO]],
               expectedStatus: Status,
               expectedBody: Option[A])(
                implicit ev: EntityDecoder[IO, A]
              ): Boolean = {
    val actualResp = actual.unsafeRunSync
    val statusCheck = actualResp.status == expectedStatus
    val bodyCheck = expectedBody.fold[Boolean](
      actualResp.body.compile.toVector.unsafeRunSync.isEmpty)( // Verify Response's body is empty.
      expected => actualResp.as[A].unsafeRunSync == expected
    )
    statusCheck && bodyCheck
  }


}
