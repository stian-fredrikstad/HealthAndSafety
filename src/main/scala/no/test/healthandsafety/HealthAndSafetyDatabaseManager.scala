package no.test.healthandsafety

import no.test.healthandsafety.model.logical.Page
import no.test.healthandsafety.model.repository.dao.PageDAO

trait HealthAndSafetyDatabaseManager {

  def pageDAO: PageDAO

  def save(page: Page): Int = pageDAO.insert(page)

  def find(id: Int): Option[Page] = pageDAO.find(id)

  def updateStatus(id: Int, s: String): Unit = pageDAO.updateStatus(id, s)
}

class HealthAndSafetyDatabaseManagerImpl(val pageDAO: PageDAO) extends HealthAndSafetyDatabaseManager