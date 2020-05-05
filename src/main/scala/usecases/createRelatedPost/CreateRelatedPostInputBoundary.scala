package usecases.createRelatedPost

import domains.post.{Post, PostRepositoryComponent}
import domains.user.UserRepositoryComponent

trait CreateRelatedPostInteractor extends UserRepositoryComponent with PostRepositoryComponent{
  def run(inputData: CreateRelatedPostInputData, presenter: ICreateRelatedPostOutputBoundary): Unit = {
    println("[start] CreateRelatedPostUseCase")

    val userId: String = inputData.userId
    val text: String = inputData.text
    val parentPostId: String = inputData.parentPostId
    val userIsExists: Boolean = userRepository.isExists(userId)
    val parentPost: Either[String, Post] = postRepository.get(parentPostId)


    parentPost.fold(
      message => presenter.run(CreateRelatedPostOutputData(Left(message))),
      pp => {
        val post: Option[Post] = Post(userId, text, Some(parentPostId), userIsExists)
        post.fold(presenter.run(CreateRelatedPostOutputData(Left("user not exists")))) { p =>
          presenter.run(CreateRelatedPostOutputData(postRepository.store(p)))
          postRepository.store(Post.addRelatedPostCount(pp))
        }
      }
    )
  }
}
