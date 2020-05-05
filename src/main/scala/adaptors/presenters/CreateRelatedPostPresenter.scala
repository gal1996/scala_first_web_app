package adaptors.presenters

import usecases.createRelatedPost.{CreateRelatedPostOutputData, ICreateRelatedPostOutputBoundary}

class CreateRelatedPostPresenter(var res: CreateRelatedPostResult) extends ICreateRelatedPostOutputBoundary {
  def run(outputData: CreateRelatedPostOutputData): Unit = {
    println("[start] CreateRelatedPostPresenter")

    outputData.post.fold(
      message => res.value = Left(CreateRelatedPostFailedResult("NG", message)),
      _ => res.value = Right(CreateRelatedPostSuccessResult("OK"))
    )
  }

  def getValue(): CreateRelatedPostResult ={
    res
  }
}
