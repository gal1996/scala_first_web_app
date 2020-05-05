package adaptors.presenters

import usecases.createpost.ICreatePostOutputBoundary
import usecases.createpost.CreatePostOutputData

class CreatePostPresenter(var res: CreatePostResult) extends ICreatePostOutputBoundary {
  def run(outPutData: CreatePostOutputData): Unit = {
    println("[start] CreatePostPresenter")

    outPutData.post.fold(
      message => res.value = Left(CreatePostFailedResult("NG", message)),
      _ => res.value = Right(CreatePostSuccessResult("OK"))
    )
  }

  def getValue(): CreatePostResult= {
    res
  }
}
