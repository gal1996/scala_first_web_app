package adaptors.controllers

import adaptors.presenters.{CreateRelatedPostFailedResult, CreateRelatedPostPresenter, CreateRelatedPostResult, CreateRelatedPostSuccessResult}
import adaptors.usecase.CreateRelatedPostUseCase
import akka.http.javadsl.server.Route
import akka.http.scaladsl.server.PathMatchers.IntNumber
import akka.http.scaladsl.server.Directives._
import spray.json.DefaultJsonProtocol.{jsonFormat1, jsonFormat2, jsonFormat6}
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
      pathPrefix("posts" / IntNumber / "create") {id =>
        entity(as[CreatePostParam]) { param =>
          run(param, id.toString)
          complete(res.value.toOption.get)
        }
      }
    }
  }

  def run(param: CreatePostParam, id: String): Unit = {
    val parentPostId: String = id
    val userId: String = param.userId
    val text: String = param.text

    CreateRelatedPostUseCase.run(CreateRelatedPostInputData(userId, text, parentPostId), createRelatedPostPresenter)
  }
}

object CreateRelatedPostController extends CreateRelatedPostController(CreateRelatedPostResult(Right(CreateRelatedPostSuccessResult(""))))
