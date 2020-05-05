package adaptors
import adaptors.controllers.CreatePostParam
import adaptors.presenters.{CreatePostFailedResult, CreatePostPresenter, CreatePostResult, CreatePostSuccessResult, GetRelatedPostsResult}
import adaptors.usecase.CreatePostUseCase
import akka.http.scaladsl.server.Directives.{complete, pathPrefix}
import spray.json.DefaultJsonProtocol.{jsonFormat1, jsonFormat2, jsonFormat6}
import usecases.createpost.CreatePostInputData
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

class CreatePostController(var res: CreatePostResult) {
  implicit val createPostSuccessResult = jsonFormat1(CreatePostSuccessResult)
  implicit val createPostFailedResult = jsonFormat2(CreatePostFailedResult)
  implicit val createPostParam = jsonFormat2(CreatePostParam)

  val CreatePostPresenter: CreatePostPresenter = new CreatePostPresenter(CreatePostResult(Right(CreatePostSuccessResult(""))))

  def route: Route = {
    post {
      path(separateOnSlashes("post/create")) {
        entity(as[CreatePostParam]) { param =>
          println("[start] CreatePost")

          run(param)

          res.value.fold(
            success => complete(success),
            fail => complete(fail)
          )
        }
      }
    }
  }

  def run(param: CreatePostParam): Unit = {
    println("[start] CreatePostController")

    val user_id: String = param.userId
    val text: String = param.text
    val inputData: CreatePostInputData = CreatePostInputData(user_id, text, None)

    CreatePostUseCase.run(inputData, CreatePostPresenter)
    res = CreatePostPresenter.getValue()
  }
}

object CreatePostController extends CreatePostController(CreatePostResult(Right(CreatePostSuccessResult(""))))
