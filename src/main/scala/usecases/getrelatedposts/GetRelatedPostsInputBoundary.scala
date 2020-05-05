package usecases.getrelatedposts

import domains.post.{Post, PostRepositoryComponent}

trait GetRelatedPostsInteractor extends PostRepositoryComponent {
  def run(inputData: GetRelatedPostsInputData, presenter: IGetRelatedPostsOutputBoundary): Unit ={
    val postId: String = inputData.postId

    val posts: Either[String, Seq[Post]] = postRepository.findByRelatedPostId(postId)
    posts.fold(
      message => presenter.run(GetRelatedPostsOutputData(Left(message))),
      p => presenter.run(GetRelatedPostsOutputData(Right(p)))
    )
  }
}
