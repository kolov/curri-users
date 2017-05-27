package curri.service.user

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class UsersServiceApp {


}

object UsersServiceApp extends App {
  SpringApplication.run(classOf[UsersServiceApp]);
}