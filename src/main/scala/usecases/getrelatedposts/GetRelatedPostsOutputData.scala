package usecases.getrelatedposts

import domains.post.Post

import scala.util.Try

case class GetRelatedPostsOutputData(posts: Either[String, Seq[Post]])
