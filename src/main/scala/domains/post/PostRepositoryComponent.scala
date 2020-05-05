package domains.post

import scala.util.Try

trait PostRepositoryComponent {
  val postRepository: PostRepository

  trait PostRepository {
    def store(post: Post): Either[String, Post]
    def findByRelatedPostId(parentPostId: String): Either[String, Seq[Post]]
    def get(id: String): Either[String, Post]
  }
}
