package adaptors.repository

import domains.user.{User, UserRepositoryComponent}

trait UsesUserRepository extends UserRepositoryComponent {
  val userRepository = UserRepositoryImpl

  object UserRepositoryImpl extends UserRepository {
    def store(user: User): User = user
    def isExists(user_id: String): Boolean = true
  }
}
