package usecases.createRelatedPost

import domains.post.Post

case class CreateRelatedPostOutputData(post: Either[String, Post])
