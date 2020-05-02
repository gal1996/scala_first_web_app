package adaptors.presenters


case class PostForResult(id: String, userId: String, text: String, parentPostId: String, relatedPostCount: Int, postedAt: String)
case class GetRelatedPostsSuccessResult(posts: Seq[PostForResult])
case class GetRelatedPostsFailedResult(result: String, message: String)
case class GetRelatedPostsResult(var value: Either[GetRelatedPostsFailedResult, GetRelatedPostsSuccessResult])

