package domains.user

trait UserRepositoryComponent {
  val userRepository: UserRepository

  trait UserRepository {
    def store(user: User): User
    def isExists(user_id: String): Boolean
  }
}
