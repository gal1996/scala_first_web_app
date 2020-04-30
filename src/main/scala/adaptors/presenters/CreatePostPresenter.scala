package adaptors.presenters

import domains.post.Post
import usecases.createpost.ICreatePostOutputBoundary
import usecases.createpost.CreatePostOutputData

trait ICreatePostPresenter extends ICreatePostOutputBoundary{
  def run(outPutData: CreatePostOutputData): Option[Post] = {
    outPutData.post
  }
}

object CreatePostPresenter extends ICreatePostPresenter