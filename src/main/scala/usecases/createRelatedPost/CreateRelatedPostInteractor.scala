package usecases.createRelatedPost

import domains.post.{Post, PostRepositoryComponent}
import domains.user.UserRepositoryComponent

trait CreateRelatedPostInteractor extends UserRepositoryComponent with PostRepositoryComponent{
  def run(inputData: CreateRelatedPostInputData, presenter: ICreateRelatedPostOutputBoundary): Unit = {
    val userId: String = inputData.userId
    val text: String = inputData.text
    val parentPostId: String = inputData.parentPostId
    val userIsExists: Boolean = userRepository.isExists(userId)
    val parentPost: Option[Post] = postRepository.get(parentPostId)

    parentPost.foreach{pp =>
      val post: Option[Post] = Post(None, userId, text, Some(parentPostId), None, None, userIsExists)
      post.foreach{p =>
        presenter.run(CreateRelatedPostOutputData(postRepository.store(p)))

        postRepository.store(Post.addRelatedPostCount(pp))
      }
    }
  }
}
