package usecases.Repositories

import domains.post.Post

import scala.concurrent.Future

trait PostRepositoryComponent {
  val postRepository: PostRepository

  trait PostRepository {
    def store(post: Post): Option[Post]
    def findByRelatedPostId(parentPostId: String): Option[Seq[Post]]
  }
}
