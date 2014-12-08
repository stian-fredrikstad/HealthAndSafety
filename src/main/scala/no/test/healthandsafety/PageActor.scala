package no.test.healthandsafety

import akka.actor.{ActorLogging, Actor}
import no.test.healthandsafety.model.logical.Page

object PageActorProtocol {
	case class FindPage(id: Int)
	case object PageNotFound
}

class PageActor extends Actor with ActorLogging {

	import no.test.healthandsafety.PageActorProtocol.{FindPage, PageNotFound}

	def receive = {
		case p: Page =>
			val id = DatabaseContext.pageDAO.insert(p)
			sender ! p.copy(id = Some(id))
		case FindPage(id) =>
			val page = DatabaseContext.pageDAO.find(id)
			page.map(sender ! _).getOrElse(sender ! PageNotFound)
	}
}
