package repository

import cats.effect.IO
import doobie.util.transactor.Transactor
import doobie.util.update

trait RepositorySpec {

  def runTests(transactor: Transactor[IO]): List[update.Update0]

}
