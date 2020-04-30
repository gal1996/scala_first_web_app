package adaptors.repository

import com.google.cloud.datastore.StructuredQuery.{OrderBy, PropertyFilter}
import domains.post.Post
import usecases.Repositories.PostRepositoryComponent
import com.google.cloud.datastore._

import scala.concurrent.Future

trait PostRepositoryComponentImpl extends PostRepositoryComponent {
  val postRepository = PostRepositoryImple
  private val instance: Datastore = DatastoreOptions.newBuilder().setProjectId("unipos-gal").build().getService
  private val kind = instance.newKeyFactory().setKind("Post")

  object PostRepositoryImple extends PostRepository {
    override def store(post: Post): Option[Post] = {
      val key = kind.newKey(post.id.toString)
      val entity = Entity.newBuilder(key)
        .set("userId", post.userId)
        .set("text", post.text)
        .set("parentPostId", post.parentPostId)
        .set("relatedPostCount", post.relatedPostCount)
        .set("postedAt", post.postedAt)
        .build()
      instance.put(entity)

      Option(post)
    }

    override def findByRelatedPostId(parentPostId: String): Option[Seq[Post]] = {
      val query =
        Query.newEntityQueryBuilder()
          .setKind("Post")
          .setFilter(PropertyFilter.eq("parentPostId", parentPostId))
          .build()

      val posts = scala.collection.mutable.ListBuffer.empty[Post]
      val queryResults = instance.run(query, ReadOption.eventualConsistency())
      while (queryResults.hasNext) {
        posts.append(entity2model(queryResults.next))
      }

      def entity2model(entity: Entity): Post = {
        Post(
          Some(entity.getKey.getName),
          entity.getString("userId"),
          entity.getString("text"),
          Some(entity.getString("parentPostId")),
          Some(entity.getLong("relatedPostCount").toInt),
          Some(entity.getString("postedAt")),
          true
        ).get
      }

      Some(posts.toSeq)
    }
  }
}



