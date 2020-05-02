package usecases.getrelatedposts

import domains.post.{Post, PostRepositoryComponent}

trait GetRelatedPostsInteractor extends PostRepositoryComponent {
  def run(inputData: GetRelatedPostsInputData, presenter: IGetRelatedPostsOutputBoundary): Unit ={
    val postId: String = inputData.postId

    val posts: Option[Seq[Post]] = postRepository.findByRelatedPostId(postId)
    posts.foreach(p => presenter.run(GetRelatedPostsOutputData(Some(p))))
  }
}
