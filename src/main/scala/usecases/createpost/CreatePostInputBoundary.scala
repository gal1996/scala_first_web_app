package usecases.createpost

import domains.post.{Post, PostRepositoryComponent}
import domains.user.UserRepositoryComponent

trait CreatePostInteractor extends PostRepositoryComponent with UserRepositoryComponent {
  def run(inputData: CreatePostInputData, presenter: ICreatePostOutputBoundary): Unit = {
    println("[start] CreatePostUseCase")

    val userId: String = inputData.userId
    val text: String = inputData.text
    val parentPostId: Option[String] = inputData.parentPostId
    val userIsExists: Boolean = userRepository.isExists(userId)
    val post: Option[Post] = Post(userId, text, parentPostId, userIsExists)

    post.fold(presenter.run(CreatePostOutputData(Left("user not exists"))))(p => presenter.run(CreatePostOutputData(postRepository.store(p))))
  }
}



