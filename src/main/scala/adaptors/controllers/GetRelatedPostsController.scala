package adaptors.controllers

import adaptors.presenters.{GetRelatedPostsFailedResult, GetRelatedPostsPresenter, GetRelatedPostsResult, GetRelatedPostsSuccessResult, PostForResult}
import adaptors.usecase.GetRelatedPostsInputBoundaryImpl
import usecases.getrelatedposts.GetRelatedPostsInputData
import akka.http.scaladsl.server.Directives.{complete, pathPrefix}
import spray.json.DefaultJsonProtocol.{jsonFormat1, jsonFormat2, jsonFormat6}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.server.Route

class GetRelatedPostsController(var res: GetRelatedPostsResult) {

  implicit val post = jsonFormat6(PostForResult)
  implicit val getRelatedPostsSuccessResult = jsonFormat1(GetRelatedPostsSuccessResult)
  implicit val getRelatedPostsFailedResult = jsonFormat2(GetRelatedPostsFailedResult)
  val getRelatedPostsPresenter = new GetRelatedPostsPresenter(GetRelatedPostsResult(Left(GetRelatedPostsFailedResult("", ""))))

  def route: Route = {
    get {
      pathPrefix("posts" / ".+".r) { id =>
        val param: GetRelatedPostsParam = GetRelatedPostsParam(id.toString)
        println("[start] getRelatedPostsController")
        run(param)

        res.value.fold(
          success => complete(success),
          fail => complete(fail)
        )
      }
    }
  }

  def run(param: GetRelatedPostsParam): Unit = {
    println("[start] getRelatedPostController")
    val postId: String = param.postId

    val inputData: GetRelatedPostsInputData = GetRelatedPostsInputData(postId)

    GetRelatedPostsInputBoundaryImpl.run(inputData, getRelatedPostsPresenter)
    res = getRelatedPostsPresenter.getValue()
  }
}

object GetRelatedPostsController extends GetRelatedPostsController(GetRelatedPostsResult(Left(GetRelatedPostsFailedResult("", ""))))
