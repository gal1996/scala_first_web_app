package usecases.createRelatedPost

trait ICreateRelatedPostOutputBoundary {
  def run(outputData: CreateRelatedPostOutputData): Unit
}
