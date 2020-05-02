package adaptors.presenters

case class CreateRelatedPostSuccessResult(result: String)
case class CreateRelatedPostFailedResult(result: String, message: String)
case class CreateRelatedPostResult(var value: Either[CreateRelatedPostFailedResult, CreateRelatedPostSuccessResult])
