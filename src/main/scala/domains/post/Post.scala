package domains.post

import java.util.{Date, UUID}

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
  // 新規作成で使用
  def apply(_userId: String, _text: String, _parentPostId: Option[String] ,userIsExists: Boolean): Option[Post] = {
    def isTextExists(text: String): Boolean = if (text != "") true else false

    val id: String = UUID.randomUUID().toString
    val userId: String = _userId
    val text: String = _text
    val parentPostId: String = _parentPostId.getOrElse("")
    val relatedPostCount: Int = 0
    val postedAt = "%tY-%<tm-%<td %<tH:%<tM:%<tS" format new Date

    if (isTextExists(text) && userIsExists) {
      val post:Post = new Post(id, userId, text, parentPostId, relatedPostCount, postedAt)
      Some(post)
    } else {
      None
    }
  }

  // 既存のDBから引いてきた時に使用する
  def apply2(_id: String, _userId: String, _text: String, _parentPostId: Option[String], _relatedPostCount: Int, _postedAt: String): Option[Post] = {
    val id: String = _id
    val userId: String = _userId
    val text: String = _text
    val parentPostId: String = _parentPostId.getOrElse("")
    val relatedPostResult: Int = _relatedPostCount
    val postedAt: String = _postedAt

    Some(new Post(id, userId, text, parentPostId, relatedPostResult, postedAt))
  }

  def addRelatedPostCount(post: Post): Post = {
    Post.apply2(post.id, post.userId, post.text, Some(post.parentPostId), post.relatedPostCount + 1, post.postedAt).get
  }
}



