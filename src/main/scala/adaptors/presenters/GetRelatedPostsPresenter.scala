package adaptors.presenters

import domains.post.Post
import usecases.getrelatedposts.{GetRelatedPostsOutputData, IGetRelatedPostsOutputBoundary}

trait IGetRelatedPostsPresenter extends IGetRelatedPostsOutputBoundary{
  def run(outputData: GetRelatedPostsOutputData): Option[Seq[Post]] = {
    outputData.posts
  }
}

object GetRelatedPostsPresenter extends IGetRelatedPostsPresenter
