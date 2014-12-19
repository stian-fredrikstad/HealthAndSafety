package no.test.healthandsafety.model.logical

case class Page(name: String, url: String, interval: Int, id: Option[Int] = None, status: Option[String] = None)