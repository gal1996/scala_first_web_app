package usecases.getrelatedposts

import domains.post.Post

case class GetRelatedPostsOutputData(posts: Option[Seq[Post]])
