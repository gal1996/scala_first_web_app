package adaptors.presenters

class Result()
case class FailedResult(result: String, message: String) extends Result
