package webapi

import adaptors.CreatePostController
import adaptors.controllers.GetRelatedPostsController
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

import scala.io.StdIn

object WebServer {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  def main(args: Array[String]) {
    val route: Route =
      CreatePostController.route~
      GetRelatedPostsController.route

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
