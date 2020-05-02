package adaptors.presenters

import usecases.createRelatedPost.{CreateRelatedPostOutputData, ICreateRelatedPostOutputBoundary}

class CreateRelatedPostPresenter(var res: CreateRelatedPostResult) extends ICreateRelatedPostOutputBoundary {
  def run(outputData: CreateRelatedPostOutputData): Unit = {
    outputData.post match {
      case Some(_) => res.value = Right(CreateRelatedPostSuccessResult("OK"))
      case _ => res.value = Left(CreateRelatedPostFailedResult("NG", "failed createPostFailedResult"))
    }
  }
}
