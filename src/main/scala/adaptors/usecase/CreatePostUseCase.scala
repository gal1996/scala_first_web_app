package adaptors.usecase

import adaptors.repository.{PostRepositoryComponentImpl, UserRepositoryComponentImpl}
import usecases.createpost.CreatePostInteractor

object CreatePostUseCase extends CreatePostInteractor with PostRepositoryComponentImpl with UserRepositoryComponentImpl
