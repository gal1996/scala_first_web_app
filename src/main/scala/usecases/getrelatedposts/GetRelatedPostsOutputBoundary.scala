package usecases.getrelatedposts

import domains.post.Post

trait IGetRelatedPostsOutputBoundary {
  def run(outputData: GetRelatedPostsOutputData): Option[Seq[Post]]
}
