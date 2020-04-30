package usecases.createpost

import domains.post.Post
import usecases.Repositories.{PostRepositoryComponent, UserRepositoryComponent}

trait ICreatePostInteractor extends PostRepositoryComponent with UserRepositoryComponent {
  def run(inputData: CreatePostInputData, presenter: ICreatePostOutputBoundary): Option[Post] = {
    val userId: String = inputData.userId
    val text: String = inputData.text
    val parentPostId: Option[String] = inputData.parentPostId
    val userIsExists: Boolean = userRepository.isExists(userId)
    val post: Option[Post] = Post(None, userId, text, parentPostId, None, None, userIsExists)

    post match {
      case Some(post) => val outputData: CreatePostOutputData = CreatePostOutputData(postRepository.store(post)); presenter.run(outputData)
      case None => val outputData: CreatePostOutputData = CreatePostOutputData(throw new OutOfMemoryError()); presenter.run(outputData)
    }
  }
}


