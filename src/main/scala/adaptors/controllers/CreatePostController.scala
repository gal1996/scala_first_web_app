package adaptors
import adaptors.controllers.CreatePostParam
import adaptors.presenters.CreatePostPresenter
import adaptors.usecase.CreatePostInputBoundaryImpl
import domains.post.Post
import usecases.createpost.CreatePostInputData

trait ICreatePostController {
  def run(param: CreatePostParam): Option[Post] = {
    val user_id: String = param.userId
    val text: String = param.text
    val inputData: CreatePostInputData = CreatePostInputData(user_id, text, None)

    CreatePostInputBoundaryImpl.run(inputData, CreatePostPresenter)
  }
}

object CreatePostController extends ICreatePostController
