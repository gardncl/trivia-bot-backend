package repository

import cats.effect.IO
import doobie.implicits._
import doobie.util.transactor.Transactor

class TeamDomainRepository(transactor: Transactor[IO]) {

  def addTeamDomain(domainName: String): IO[Int] =
    sql"""INSERT INTO team_domains (name)
         |VALUES ($domainName)"""
      .update
      .withUniqueGeneratedKeys[Int]("id", "name")
      .transact(transactor)

}
