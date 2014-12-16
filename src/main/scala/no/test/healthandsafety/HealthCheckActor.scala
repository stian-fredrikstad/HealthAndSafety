package no.test.healthandsafety

import akka.actor.Actor.Receive
import akka.actor.{ActorLogging, Actor}
import no.test.healthandsafety.model.logical.Page

import scalaj.http.{Http, HttpResponse}

class HealthCheckActor extends Actor with ActorLogging {

  def receive: Receive = {
    case p: Page =>
      log.debug(s"Checking $p")
      val response: HttpResponse[String] = Http(p.url).asString
      p.id.map(i => DatabaseContext.pageDAO.updateStatus(i, response.statusLine))
  }
}
