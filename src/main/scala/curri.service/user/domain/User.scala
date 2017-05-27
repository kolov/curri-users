package curri.service.user.domain

import java.util.UUID
import org.springframework.data.annotation.Id

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonProperty}
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.{DBRef, Document}

import scala.beans.BeanProperty

@Document
case class User() extends Serializable {

  @Id
  @JsonProperty("id")
  var id: String = _

  @BeanProperty
  @Indexed
  @JsonProperty("cookieValue")
  var cookieValue: String = _

  @BeanProperty
  @JsonProperty("acceptsCookies")
  var acceptsCookies: Boolean = _

  @DBRef
  @BeanProperty
  @JsonProperty("identity")
  var identity: Identity = _

  def wipe = {
    cookieValue = UUID.randomUUID().toString
    acceptsCookies = false
    identity = null
  }

}