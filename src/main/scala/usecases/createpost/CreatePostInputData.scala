package usecases.createpost

case class CreatePostInputData(userId: String, text: String, parentPostId: Option[String])

