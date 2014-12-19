package no.test.healthandsafety

import akka.actor.{Cancellable, Props, Actor, ActorLogging}
import no.test.healthandsafety.PageActorProtocol.{ScheduleStarted, ScheduleStopped}
import no.test.healthandsafety.model.repository.dao.PageId
import scala.concurrent.duration._

case class StopSchedule(id: PageId)
case class StartSchedule(id: PageId)

class HealthCheckScheduleActor(actorProps: HealthCheckActorProps,
                               databaseManager: HealthAndSafetyDatabaseManager) extends Actor with ActorLogging {

  implicit def executionContext = context.dispatcher
  val healthCheckActor = context.actorOf(actorProps.healthCheckActor)

  var schedules: Map[PageId, Cancellable] = Map.empty

  def receive: Receive = {
    case StartSchedule(id) => {
      log.debug(s"Trying to start schedule for id: $id")
      databaseManager.find(id.id).foreach(p => {
        val cancellableHealthCheckActor: Cancellable = schedules.getOrElse(id,
          context.system.scheduler.schedule(0 milliseconds,
            p.interval seconds,
            healthCheckActor,
            p)
        )
        schedules = schedules + (id -> cancellableHealthCheckActor)
        log.debug(s"Number of schedules: ${schedules.size}")
      })
      sender() ! ScheduleStarted
    }
    case StopSchedule(id) => {
      log.debug(s"Trying to cancel $id for sender $sender ${schedules.size}")
      schedules.get(id).foreach(c => c.cancel())
      schedules = schedules - id
      sender() ! ScheduleStopped
    }
  }
}
