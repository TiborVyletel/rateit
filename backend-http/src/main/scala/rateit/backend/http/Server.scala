package rateit.backend.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.{getFromResourceDirectory, _}
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.util.Properties

/**
  * Created by Tibor Vyletel on 2017-04-03.
  */
object Server {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    val port = Properties.envOrElse("PORT", "8080").toInt
    val route: Route = indexRoute ~ ratingServiceRoute

    Http().bindAndHandle(route, "0.0.0.0", port = port)
  }

  val indexRoute = pathSingleSlash {
    get {
      complete {
        HttpEntity(
                    ContentTypes.`text/html(UTF-8)`,
                    Index.render()
                  )
      }
    }
  } ~ get {path("html-ui-fastopt.js")(
                                                          getFromResource("html-ui-fastopt.js")) } ~
    getFromResourceDirectory("META-INF/resources/webjars/backendhttp/0.1.0")
//  val resourcesRoute =

  val ratingServiceRoute: Route = {
    logRequest("service-call") {
      path("ratings" / Segment) { id =>
        complete(s"path: $id")
      }
    }
  }

}
