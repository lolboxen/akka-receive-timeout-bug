import akka.actor.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.adapter._
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.duration.DurationInt

object Main {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("unimportant")
    system.spawn(Evidence(), "evidence")
  }

  object Evidence {
    trait Command
    case object LayTrap extends Command
    case object SpringTrap extends Command
    case object Timeout extends Command

    def apply(): Behavior[Command] =
      Behaviors.setup { context =>
        Behaviors.withTimers { timers =>

          context.self ! LayTrap

          Behaviors.receiveMessage {
            case LayTrap =>
              context.setReceiveTimeout(100.milliseconds, Timeout)
              timers.startSingleTimer(SpringTrap, 100.milliseconds)
              Behaviors.same

            case SpringTrap =>
              context.cancelReceiveTimeout()
              Behaviors.same

            case Timeout =>
              context.log.info("received timeout, test failed")
              Behaviors.stopped
          }
        }
      }
  }
}
