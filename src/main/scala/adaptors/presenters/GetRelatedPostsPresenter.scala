package adaptors.presenters

import usecases.getrelatedposts.{GetRelatedPostsOutputData, IGetRelatedPostsOutputBoundary}
import scala.collection.mutable.ListBuffer

class GetRelatedPostsPresenter(var res: GetRelatedPostsResult) extends IGetRelatedPostsOutputBoundary{
  def run(outputData: GetRelatedPostsOutputData): Unit = {
    var postsForRes =  ListBuffer.empty[PostForResult]
    outputData.posts.foreach(_.foreach(p => postsForRes += PostForResult(p.id, p.userId, p.text, p.parentPostId, p.relatedPostCount, p.postedAt)))

    outputData.posts match {
      case Some(_) => res.value = Right(GetRelatedPostsSuccessResult(postsForRes.toSeq))
      case None => res.value = Left(GetRelatedPostsFailedResult("NG", "failed getRelatedPost"))
    }
  }

  def getValue(): GetRelatedPostsResult = {
    res
  }
}

