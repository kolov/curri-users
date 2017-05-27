package curri.service.user.web

import curri.service.user.persist.{IdentityRepository, UserRepository}
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@RunWith(classOf[SpringRunner])
@WebMvcTest(Array(classOf[UserController]))
@WebAppConfiguration
class UserControllerIT {

  @Autowired
  var mvc: MockMvc = _

  @MockBean
  var userService: UserRepository = _

  @MockBean
  var identityRepository: IdentityRepository = _

  @Test
  def queryBadParams(): Unit = {
    mvc.perform(MockMvcRequestBuilders.get("/query")
      .accept(MediaType.APPLICATION_JSON)
      .param("XXX", "123"))
      .andExpect(MockMvcResultMatchers.status().is(400));
  }

  @Test
  def queryGoodParams(): Unit = {
    mvc.perform(MockMvcRequestBuilders.get("/query")
      .accept(MediaType.APPLICATION_JSON)
      .param("cookie", "123")
      .param("create", "false"))
      .andExpect(MockMvcResultMatchers.status().is(404));
  }
}
