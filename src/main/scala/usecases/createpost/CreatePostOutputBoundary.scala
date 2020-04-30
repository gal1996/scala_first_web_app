package usecases.createpost

import domains.post.Post

trait ICreatePostOutputBoundary {
  def run(outPutData: CreatePostOutputData): Option[Post]
}

