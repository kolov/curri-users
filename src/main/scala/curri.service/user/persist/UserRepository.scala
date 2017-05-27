package curri.service.user.persist

import curri.service.user.domain.User
import org.springframework.data.mongodb.repository.MongoRepository

trait UserRepository extends MongoRepository[User, String] {
  def findByCookieValue(cookieId: String): User
}