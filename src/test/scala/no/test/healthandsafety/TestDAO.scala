package no.test.healthandsafety

import no.test.healthandsafety.domain.DBConfig
import no.test.healthandsafety.infrastructure.SlickHealthAndSafetyDAO

import scala.slick.driver.{H2Driver, JdbcProfile}
import scala.slick.jdbc.JdbcBackend._


trait TestDAO extends TestDBConfig {
	val dao: SlickHealthAndSafetyDAO = new SlickHealthAndSafetyDAO(this)
	//dao.createDB()

}

trait TestDBConfig extends DBConfig {
	val profile: JdbcProfile = H2Driver
	val db: Database = Database.forURL("jdbc:h2:mem:" + databaseName + ";DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

	def databaseName: String
}
