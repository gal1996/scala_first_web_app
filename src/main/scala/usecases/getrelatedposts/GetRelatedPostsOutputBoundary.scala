package usecases.getrelatedposts

trait IGetRelatedPostsOutputBoundary {
  def run(outputData: GetRelatedPostsOutputData): Unit
}
