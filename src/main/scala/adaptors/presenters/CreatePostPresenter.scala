package adaptors.presenters

import usecases.createpost.ICreatePostOutputBoundary
import usecases.createpost.CreatePostOutputData

class CreatePostPresenter(var res: CreatePostResult) extends ICreatePostOutputBoundary {
  def run(outPutData: CreatePostOutputData): Unit = {
    outPutData.post match {
      case Some(_) => res.value = Right(CreatePostSuccessResult("OK"))
      case _ => res.value = Left(CreatePostFailedResult("NG", "failed createPost"))
    }
  }

  def getValue(): CreatePostResult= {
    res
  }
}
