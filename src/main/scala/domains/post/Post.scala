package domains.post

import java.util.{Date, UUID}

import domains.user.UserRepositoryComponent

class Post private (
  val id: String,
  val userId: String,
  val text: String,
  val parentPostId: String,
  val relatedPostCount: Int,
  val postedAt: String
) {

}


object Post {
  def apply(_id: Option[String], _userId: String, _text: String, _parentPostId: Option[String], _relatedPostCount: Option[Int], _postedAt: Option[String], userIsExists: Boolean): Option[Post] = {
    def isTextExists(text: String): Boolean = if (text != "") true else false

    val id: String = _id.getOrElse(UUID.randomUUID().toString)
    val userId: String = _userId
    val text: String = _text
    val ParentPostId: String = _parentPostId.getOrElse("")
    val RelatedPostCount: Int = _relatedPostCount.getOrElse(0)
    val postedAt = _postedAt.getOrElse("%tY-%<tm-%<td %<tH:%<tM:%<tS" format new Date)

    if (isTextExists(text) && userIsExists) {
      val post:Post = new Post(id, userId, text, ParentPostId, RelatedPostCount, postedAt)
      Some(post)
    } else {
      None
    }
  }

  def addRelatedPostCount(post: Post): Post = {
    Post(Some(post.id), post.userId, post.text, Some(post.parentPostId), Some(post.relatedPostCount + 1), Some(post.postedAt), true).get
  }
}



