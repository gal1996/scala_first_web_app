package domains.user

import java.util.UUID

class User private (
  val id: UUID,
  val name: String
){}
