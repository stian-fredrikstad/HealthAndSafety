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

case class NewPageJSON(name: String, url: String, interval: Int) {
	def toPage: Page = Page(name = name, url = url, interval = interval)
}

class PageActor(actorProps: HealthCheckActorProps,
								 databaseManager: HealthAndSafetyDatabaseManager) extends Actor with ActorLogging {

	import no.test.healthandsafety.PageActorProtocol.{FindPage, PageNotFound}

	val healthCheckScheduleActor = context.actorOf(actorProps.healthCheckScheduleActorProps, "health-check-schedule-actor")

	def receive = {
		case p: NewPageJSON =>
			log.debug(s"Handling new page $p")
			val page: Page = p.toPage
			val id = databaseManager.save(p.toPage)
			healthCheckScheduleActor forward StartSchedule(PageId(id))
			sender ! page.copy(id = Some(id))
		case s: StopSchedule =>
			healthCheckScheduleActor.forward(s)
		case s: StartSchedule =>
			healthCheckScheduleActor.forward(s)
		case FindPage(id) =>
			val page = databaseManager.find(id)
			page.map(sender ! _).getOrElse(sender ! PageNotFound)
	}
}
