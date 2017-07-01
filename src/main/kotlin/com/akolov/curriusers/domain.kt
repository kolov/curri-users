package com.akolov.curriusers

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.repository.MongoRepository

class Identity(

        @Id
        @JsonIgnore
        var id: String? = null,


        var xx: String? = null

)

data class User(
        @Id
        var id: String? = null

)

interface IdentityRepository : MongoRepository<Identity, String> {

}
interface UserRepository : MongoRepository<User, String> {

}