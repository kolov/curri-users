package com.akolov.curriusers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ControllerAdvice
import java.lang.Exception
import java.util.*


class NotFoundException : Exception()

@RestController
@RequestMapping("/user")
class UsersController(val userRepository: UserRepository,
                      val identityRepository: IdentityRepository) {


    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/{id}")
    @ResponseBody
    open fun user(@PathVariable("id") id: String): User {
        return userRepository.findOne(id) ?: throw NotFoundException()
    }

    @RequestMapping(method = arrayOf(RequestMethod.POST), value = "/create")
    @ResponseBody
    open fun create(): User {
        val user = User(UUID.randomUUID().toString())
        return userRepository.save(user)
    }

}

@ControllerAdvice
internal class ControllerAdvice {

    @ResponseBody
    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun userNotFoundExceptionHandler(ex: NotFoundException): String {
        return "not found"
    }
}