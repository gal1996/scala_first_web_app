package adaptors.presenters

case class CreatePostSuccessResult(result: String)
case class CreatePostFailedResult(result: String, message: String)
case class CreatePostResult(var value: Either[CreatePostFailedResult, CreatePostSuccessResult])
