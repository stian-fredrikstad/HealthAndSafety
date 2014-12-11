package no.test.healthandsafety.model.logical

case class Page(url: String, interval: Int, id: Option[Int] = None, status: Option[String] = None)