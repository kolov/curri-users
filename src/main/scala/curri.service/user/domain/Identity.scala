package curri.service.user.domain

import com.akolov.curri.web.oauth.IdentityCodesType
import org.springframework.data.annotation.Id
import com.fasterxml.jackson.annotation.{JsonIgnore, JsonProperty}
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

@Document
class Identity extends Serializable {

  @Id
  @JsonIgnore
  var id: String = _

  @BeanProperty
  @JsonProperty("provider")
  @JsonScalaEnumeration(classOf[IdentityCodesType])
  //var provider: IdentityProvider.IdentityProvider = _
  // Scala Enumeration doesn't work out of the box with MongoDB, use string
  var providerCode: String = _

  @BeanProperty
  @JsonProperty("firstName")
  var firstName: String = _

  @BeanProperty
  @JsonProperty("lastName")
  var lastName: String = _

  @BeanProperty
  @JsonProperty("name")
  var name: String = _

  @BeanProperty
  @JsonProperty("link")
  var link: String = _

  @BeanProperty
  @JsonProperty("picture")
  var picture: String = _

  @BeanProperty
  @JsonProperty("gender")
  var gender: String = _

  @BeanProperty
  @JsonProperty("remoteId")
  var remoteId: String = _

}
