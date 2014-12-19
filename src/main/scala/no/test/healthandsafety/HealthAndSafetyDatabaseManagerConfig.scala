package no.test.healthandsafety

import no.test.healthandsafety.infrastructure.repository.{SlickDataSourceInMemory, HealthAndSafetyDatabaseSlickComponent}

trait HealthAndSafetyDatabaseManagerConfig extends SlickDataSourceInMemory with HealthAndSafetyDatabaseSlickComponent {
  def databaseManager: HealthAndSafetyDatabaseManager = new HealthAndSafetyDatabaseManagerImpl(pageDAO)

}
