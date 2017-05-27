package curri.service.user.web

import com.akolov.curri.web.oauth.Codes.FACEBOOK
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import curri.service.user.domain.{Identity, User}
import curri.service.user.persist.{IdentityRepository, UserRepository}
import org.assertj.core.api.Assertions
import org.junit.runner.RunWith
import org.junit.{Before, Test}
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.util.ReflectionTestUtils

@RunWith(classOf[SpringRunner])
@JsonTest
@SpringBootTest
class UserControllerJsonIT {

  var json: JacksonTester[curri.service.user.domain.User] = _

  @Before
  def setup(): Unit = {
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    JacksonTester.initFields(this, mapper)
  }


  //normally created by spring-data, needed to initialize context
  @MockBean
  var userRepo: UserRepository = _
  @MockBean
  var identotyRepo: IdentityRepository = _


  @Test
  def exampleTest(): Unit = {

    val user: User = new User()
    val identity = new Identity()
    identity.setProviderCode(FACEBOOK.toString)
    identity.setFirstName("John")
    identity.setLastName("Doe")
    identity.setRemoteId("12345")

    ReflectionTestUtils.setField(user, "cookieValue", "abcde")
    ReflectionTestUtils.setField(user, "identity", identity)

    System.out.println(json.write(user))

    Assertions.assertThat(json.write(user)).isEqualToJson("user-1-test.json")

  }
}
