package usecases.getrelatedposts

import domains.post.Post
import usecases.Repositories.PostRepositoryComponent

trait IGetRelatedPostsInputBoundary extends PostRepositoryComponent {
  def run(inputData: GetRelatedPostsInputData, presenter: IGetRelatedPostsOutputBoundary): Option[Seq[Post]] ={
    val postId: String = inputData.postId

    val posts: Option[Seq[Post]] = postRepository.findByRelatedPostId(postId)
    posts match {
      case Some(posts) => val outputData: GetRelatedPostsOutputData = GetRelatedPostsOutputData(Some(posts)); presenter.run(outputData)
      case None => val outputData: GetRelatedPostsOutputData = GetRelatedPostsOutputData(None); presenter.run(outputData)
    }
  }
}
