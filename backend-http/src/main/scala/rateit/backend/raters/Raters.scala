package rateit.backend.raters

import java.time.ZonedDateTime

import akka.typed._
import akka.typed.scaladsl.Actor.Stateful
import akka.typed.scaladsl.Actor.Same
import akka.typed.scaladsl.ActorContext

trait Score {
  def toPercentage: Double
}

sealed case class StarScore(stars: Int) extends Score {
  override def toPercentage: Double = stars / 5 * 100;
}

object StarScore {

  implicit def intToStarScore(stars: Int) = {
    stars match {
      case 1 => `1*`
      case 2 => `2*s`
      case 3 => `3*s`
      case 4 => `4*s`
      case 5 => `5*s`
    }
  }
}

object `1*` extends StarScore(1)

object `2*s` extends StarScore(2)

object `3*s` extends StarScore(3)

object `4*s` extends StarScore(4)

object `5*s` extends StarScore(5)

sealed trait RaterCmd

sealed trait RaterOutcome

case class AddRating(text: String, score: Score, reportedBy: String, reportedAt: ZonedDateTime = ZonedDateTime.now())
  extends RaterCmd

case class ShowStats(subject: String, replyTo: ActorRef[RaterOutcome]) extends RaterCmd

case class BasicStats(subject: String, since: ZonedDateTime, ratingsCount: Long, percentage: Double,
  lastRating: ZonedDateTime) extends RaterOutcome


case class SubjectsRatings(subject: String, texts: Seq[String] = Seq.empty) {

  def ++(t: (String, Score)): SubjectsRatings = copy(texts = this.texts :+ t._1)
}

case object SubjectsRatings {

  def empty(subject: String) = SubjectsRatings(subject)

}

/**
  * Created by Tibor Vyletel on 2017-04-27.
  */
object Raters {

  //  def createNewSubject

  def rating(ratings: SubjectsRatings): Behavior[RaterCmd] = Stateful[RaterCmd] {
    (ctx: ActorContext[RaterCmd], msg: RaterCmd) =>
      msg match {
        case AddRating(text, score, _, _) =>
          rating(ratings ++ (text, score))
        case ShowStats(subject, client) =>
          client ! BasicStats(subject, ZonedDateTime.now(), 0, 0, ZonedDateTime.now())
          Same
      }
  }

}
