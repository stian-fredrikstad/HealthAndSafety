package no.test.healthandsafety

import no.test.healthandsafety.domain.{HealthAndSafetyDAO, DBConfig}
import no.test.healthandsafety.infrastructure.SlickHealthAndSafetyDAO

trait DAOConfig {
	def dbConfig: DBConfig
	def dao: HealthAndSafetyDAO = new SlickHealthAndSafetyDAO(dbConfig)
}
