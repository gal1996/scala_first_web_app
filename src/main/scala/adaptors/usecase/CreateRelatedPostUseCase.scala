package adaptors.usecase

import adaptors.repository.{UsesPostRepository, UsesUserRepository}
import usecases.createRelatedPost.CreateRelatedPostInteractor

object CreateRelatedPostUseCase extends CreateRelatedPostInteractor with UsesPostRepository with UsesUserRepository

