package adaptors.controllers

import adaptors.presenters.GetRelatedPostsPresenter
import adaptors.usecase.GetRelatedPostsInputBoundaryImpl
import domains.post.Post
import usecases.getrelatedposts.GetRelatedPostsInputData

trait IGetRelatedPostsController {
  def run(param: GetRelatedPostsParam): Option[Seq[Post]] = {
    val postId: String = param.postId

    val inputData: GetRelatedPostsInputData = GetRelatedPostsInputData(postId)

    GetRelatedPostsInputBoundaryImpl.run(inputData, GetRelatedPostsPresenter)
  }
}

object GetRelatedPostsController extends IGetRelatedPostsController
