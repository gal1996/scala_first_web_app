package webapi

import adaptors.CreatePostController
import adaptors.controllers.{CreatePostParam, GetRelatedPostsController, GetRelatedPostsParam}
import adaptors.presenters.{CreatePostResult, FailedResult, GetRelatedPostsResult, Result}
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import domains.post.Post

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

import scala.io.StdIn

object WebServer {

  // needed to run the route
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  // needed for the future map/flatmap in the end and future in fetchItem and saveOrder
  implicit val executionContext = system.dispatcher

  // domain model
  final case class PostForResult(val id: String, val userId: String, val text: String, val parentPostId: String, val relatedPostCount: Int, val postedAt: String)

  implicit val createPostParam = jsonFormat2(CreatePostParam)
  implicit val createPostResult = jsonFormat1(CreatePostResult)
  implicit val failedResult = jsonFormat2(FailedResult)
  implicit val postForResult = jsonFormat6(PostForResult)
  implicit val getRelatedPostsResult = jsonFormat1(GetRelatedPostsResult)

  def main(args: Array[String]) {
    val route: Route =
      concat(
        get {
          pathPrefix("posts" / IntNumber) { id =>
            // there might be no item for a given id
            val param: GetRelatedPostsParam = GetRelatedPostsParam(id.toString)
            val result: Option[Seq[Post]] = GetRelatedPostsController.run(param)

            result.foreach(seq => seq.foreach(p => p.asInstanceOf[PostForResult]))

            result match {
              case posts: Seq[PostForResult] => complete(GetRelatedPostsResult(posts))
              case None => complete(FailedResult("NG", "failed getRelatedPosts"))
            }
          }
        },
        post {
          path("posts/create") {
            entity(as[CreatePostParam]) { param =>
              val result: Option[Post] = CreatePostController.run(param)
              result match {
                case Some(_) => complete(CreatePostResult("OK"))
                case None => complete(FailedResult("NG", "failed createPost"))
              }
            }
          }
        }
      )

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
