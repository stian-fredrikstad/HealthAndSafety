package no.test.healthandsafety

import akka.actor.{Props, ActorLogging, Actor}
import no.test.healthandsafety.model.logical.Page
import no.test.healthandsafety.model.repository.dao.PageId

object PageActorProtocol {
	case class FindPage(id: Int)
	case object PageNotFound
	case object ScheduleStopped
	case object ScheduleStarted
}

class PageActor extends Actor with ActorLogging {

	import no.test.healthandsafety.PageActorProtocol.{FindPage, PageNotFound}

	val healthCheckScheduleActor = context.actorOf(Props[HealthCheckScheduleActor])

	def receive = {
		case p: Page =>
			log.debug(s"Handling page $p")
			val id = DatabaseContext.pageDAO.insert(p)
			healthCheckScheduleActor forward StartSchedule(PageId(id))
			sender ! p.copy(id = Some(id))
		case s: StopSchedule =>
			healthCheckScheduleActor.forward(s)
		case s: StartSchedule =>
			healthCheckScheduleActor.forward(s)
		case FindPage(id) =>
			val page = DatabaseContext.pageDAO.find(id)
			page.map(sender ! _).getOrElse(sender ! PageNotFound)
	}
}
