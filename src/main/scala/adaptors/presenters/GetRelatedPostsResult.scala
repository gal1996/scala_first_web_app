package adaptors.presenters

//import domains.post.Post
import webapi.WebServer.PostForResult

case class GetRelatedPostsResult(relatedPosts: Seq[PostForResult]) extends Result
