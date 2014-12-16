package no.test.healthandsafety.model.repository.dao

import no.test.healthandsafety.model.logical.Page

case class PageId(id: Int)

trait PageDAO {
	def insert(page: Page): Int
	def find(id: Int): Option[Page]
	def create(): Unit
	def updateStatus(id: Int, status: String): Unit
}