package usecases.createpost

import domains.post.{Post, PostRepositoryComponent}
import domains.user.UserRepositoryComponent

trait CreatePostInteractor extends PostRepositoryComponent with UserRepositoryComponent {
  def run(inputData: CreatePostInputData, presenter: ICreatePostOutputBoundary): Unit = {
    val userId: String = inputData.userId
    val text: String = inputData.text
    val parentPostId: Option[String] = inputData.parentPostId
    val userIsExists: Boolean = userRepository.isExists(userId)
    val post: Option[Post] = Post(None, userId, text, parentPostId, None, None, userIsExists)

    post.foreach(p => presenter.run(CreatePostOutputData(postRepository.store(p))))
  }
}



