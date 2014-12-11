package no.test.healthandsafety

import akka.actor.{Props, ActorLogging, Actor}
import no.test.healthandsafety.model.logical.Page
import scala.concurrent.duration._

object PageActorProtocol {
	case class FindPage(id: Int)
	case object PageNotFound
}

class PageActor extends Actor with ActorLogging {

	import no.test.healthandsafety.PageActorProtocol.{FindPage, PageNotFound}
	implicit def executionContext = context.dispatcher

	val healthCheckActor = context.actorOf(Props[HealthCheckActor])

	def receive = {
		case p: Page =>
			val id = DatabaseContext.pageDAO.insert(p)
			context.system.scheduler.schedule(0 milliseconds,
				p.interval seconds,
				healthCheckActor,
				Page(p.url, p.interval, Option(id)))
			sender ! p.copy(id = Some(id))
		case FindPage(id) =>
			val page = DatabaseContext.pageDAO.find(id)
			page.map(sender ! _).getOrElse(sender ! PageNotFound)
	}
}
