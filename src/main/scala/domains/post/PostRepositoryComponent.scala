package domains.post

trait PostRepositoryComponent {
  val postRepository: PostRepository

  trait PostRepository {
    def store(post: Post): Option[Post]
    def findByRelatedPostId(parentPostId: String): Option[Seq[Post]]
    def get(id: String): Option[Post]
  }
}
