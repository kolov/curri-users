package com.akolov.curriusers

import org.springframework.data.domain.Example
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

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/details/{id}")
    @ResponseBody
    open fun details(@PathVariable("id") id: String): User {
        val user = userRepository.findOne(id) ?: throw NotFoundException()

        val example = Example.of(Identity(userId = id))
        val identity = identityRepository.findOne(example)
        if (identity == null) {
            return user
        } else {
            return user.copy(identity = identity)
        }

    }

    @RequestMapping(method = arrayOf(RequestMethod.POST), value = "/create")
    @ResponseBody
    open fun create(): User {
        val user = User(UUID.randomUUID().toString())
        return userRepository.save(user)
    }

    @RequestMapping(method = arrayOf(RequestMethod.PUT), value = "/{currentUserId}/identity")
    @ResponseBody
    open fun register(@PathVariable currentUserId: String, @RequestBody identity: Identity): User {
        val user = findUnregisterdUser(currentUserId)
        identityRepository.save(identity.copy(userId = user.id))
        return user
    }

    private fun findUnregisterdUser(currentUserId: String): User {
        val existingUser = userRepository.findOne(currentUserId)
        if (existingUser != null) {
            val example = Example.of(Identity(userId = currentUserId))
            identityRepository.findOne(example) ?: return User(currentUserId)
        }

        val user = User(UUID.randomUUID().toString())
        userRepository.save(user)
        return user
    }

}

@RestController
@RequestMapping("/identity")
class IdentityController(val userRepository: UserRepository,
                         val identityRepository: IdentityRepository) {

    @RequestMapping(method = arrayOf(RequestMethod.POST), value = "/")
    @ResponseBody
    open fun find(sub: String): Identity {
        return identityRepository.findOne(sub) ?: throw NotFoundException()
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