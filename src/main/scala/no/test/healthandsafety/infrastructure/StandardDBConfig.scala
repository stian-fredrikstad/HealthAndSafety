package no.test.healthandsafety.infrastructure

import no.test.healthandsafety.domain.DBConfig

import scala.slick.driver.{H2Driver, JdbcProfile}
import scala.slick.jdbc.JdbcBackend._

object StandardDBConfig extends DBConfig {
	val profile: JdbcProfile = H2Driver
	val db: Database = Database.forURL("jdbc:h2:mem:healthandsafety;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
}
