package adaptors.controllers

import adaptors.presenters.{CreateRelatedPostFailedResult, CreateRelatedPostPresenter, CreateRelatedPostResult, CreateRelatedPostSuccessResult}
import adaptors.usecase.CreateRelatedPostUseCase
import akka.http.scaladsl.server.Directives._
import spray.json.DefaultJsonProtocol.{jsonFormat1, jsonFormat2}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server
import spray.json.DefaultJsonProtocol._
import usecases.createRelatedPost.CreateRelatedPostInputData

class CreateRelatedPostController(var res: CreateRelatedPostResult) {
  implicit val createPostParam = jsonFormat2(CreatePostParam)
  implicit val createRelatedPostSuccessResult = jsonFormat1(CreateRelatedPostSuccessResult)
  implicit val createRelatedPostFailedResult = jsonFormat2(CreateRelatedPostFailedResult)

  val createRelatedPostPresenter = new CreateRelatedPostPresenter(CreateRelatedPostResult(Right(CreateRelatedPostSuccessResult(""))))

  def route: server.Route = {
    post {
      pathPrefix("posts" / ".+".r / "create") {id =>
        entity(as[CreatePostParam]) { param =>
          println("[start] CreateRelatedPost")

          run(param, id)

          res.value.fold(
            success => complete(success),
            fail => complete(fail)
          )
        }
      }
    }
  }

  def run(param: CreatePostParam, id: String): Unit = {
    println("[start] CreateRelatedPostController")
    val parentPostId: String = id
    val userId: String = param.userId
    val text: String = param.text

    CreateRelatedPostUseCase.run(CreateRelatedPostInputData(userId, text, parentPostId), createRelatedPostPresenter)
    res = createRelatedPostPresenter.getValue()
  }
}

object CreateRelatedPostController extends CreateRelatedPostController(CreateRelatedPostResult(Right(CreateRelatedPostSuccessResult(""))))
