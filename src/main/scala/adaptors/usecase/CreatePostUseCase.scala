package adaptors.usecase

import adaptors.repository.{UsesPostRepository, UsesUserRepository}
import usecases.createpost.CreatePostInteractor

object CreatePostUseCase extends CreatePostInteractor with UsesPostRepository with UsesUserRepository
