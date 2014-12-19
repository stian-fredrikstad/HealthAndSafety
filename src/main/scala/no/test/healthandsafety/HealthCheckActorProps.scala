package no.test.healthandsafety

import akka.actor.Props

trait HealthCheckActorProps {

  def healthCheckActor: Props

  def healthCheckScheduleActorProps: Props

  def pageActorProps: Props
}

trait StandardHealthCheckActorProps extends HealthCheckActorProps with HealthAndSafetyDatabaseManagerConfig {

  def healthCheckActor: Props = Props(classOf[HealthCheckActor], databaseManager)

  def pageActorProps: Props = Props(classOf[PageActor], this, databaseManager)

  def healthCheckScheduleActorProps: Props = Props(classOf[HealthCheckScheduleActor], this, databaseManager)
}
