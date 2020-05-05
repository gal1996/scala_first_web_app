package adaptors.repository

import java.io.FileInputStream

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.datastore.StructuredQuery.{OrderBy, PropertyFilter}
import domains.post.{Post, PostRepositoryComponent}
import com.google.cloud.datastore._

trait UsesPostRepository extends PostRepositoryComponent {
  val postRepository = PostRepositoryImpl

  private val credentials: GoogleCredentials = GoogleCredentials.fromStream(new FileInputStream("/Users/yugo_tsuchiya/Downloads/My First Project-422a504aa625.json"))
  private val instance: Datastore = DatastoreOptions.newBuilder().setCredentials(credentials).setProjectId("poised-cathode-235316").build.getService
  private val kind = instance.newKeyFactory().setKind("Post")

  object PostRepositoryImpl extends PostRepository {
    override def store(post: Post): Either[String, Post] = {
      println("[start] PostRepository.store")

      val key = kind.newKey(post.id)
      val entity = Entity.newBuilder(key)
        .set("userId", post.userId)
        .set("text", post.text)
        .set("parentPostId", post.parentPostId)
        .set("relatedPostCount", post.relatedPostCount)
        .set("postedAt", post.postedAt)
        .build()
      val res = instance.put(entity)
      if (res != null) Right(post) else Left("datastore fail")
    }

    override def findByRelatedPostId(parentPostId: String): Either[String, Seq[Post]] = {
      println("[start] PostRepository.findByRelatedPostId")
      val query =
        Query.newEntityQueryBuilder()
          .setKind("Post")
          .setFilter(PropertyFilter.eq("parentPostId", parentPostId))
          .build()

      val posts = scala.collection.mutable.ListBuffer.empty[Post]
      val queryResults = instance.run(query, ReadOption.eventualConsistency())
      if (!queryResults.hasNext) {
        Left("datastore fail")
      } else {
        while (queryResults.hasNext) {
          posts.append(entity2model(queryResults.next))
        }
        Right(posts.toSeq)
      }

    }

    override def get(id: String): Either[String, Post] = {
      println("[start] PostRepository.get")
      val key = kind.newKey(id)
      val result = instance.get(key, ReadOption.eventualConsistency())

      if (result != null) Right(entity2model(result)) else Left("datastor fail")
    }

    def entity2model(entity: Entity): Post = {
      Post.apply2(
        entity.getKey.getName,
        entity.getString("userId"),
        entity.getString("text"),
        Some(entity.getString("parentPostId")),
        entity.getLong("relatedPostCount").toInt,
        entity.getString("postedAt"),
      ).get
    }
  }
}
