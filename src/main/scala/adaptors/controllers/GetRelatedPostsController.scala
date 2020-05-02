package adaptors.controllers

import adaptors.presenters.{GetRelatedPostsFailedResult, GetRelatedPostsPresenter, GetRelatedPostsResult, GetRelatedPostsSuccessResult, PostForResult}
import adaptors.usecase.GetRelatedPostsInputBoundaryImpl
import domains.post.Post
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
      pathPrefix("posts" / IntNumber) { id =>
        // there might be no item for a given id
        val param: GetRelatedPostsParam = GetRelatedPostsParam(id.toString)
        run(param)

        complete(res.value.toOption.get)
      }
    }
  }

  def run(param: GetRelatedPostsParam): Unit = {
    val postId: String = param.postId

    val inputData: GetRelatedPostsInputData = GetRelatedPostsInputData(postId)

    GetRelatedPostsInputBoundaryImpl.run(inputData, getRelatedPostsPresenter)
    res = getRelatedPostsPresenter.getValue()
  }
}

object GetRelatedPostsController extends GetRelatedPostsController(GetRelatedPostsResult(Left(GetRelatedPostsFailedResult("", ""))))
