package usecases.createpost

trait ICreatePostOutputBoundary {
  def run(outPutData: CreatePostOutputData): Unit
}

