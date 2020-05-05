package usecases.createpost

import domains.post.Post

case class CreatePostOutputData(post: Either[String, Post])
