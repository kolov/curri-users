package com.akolov.curriusers

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository

@Document
data class Identity(

        @Id
        var cap: String? = null,

        var userId: String? = null,
        var givenName: String? = null,
        var familyName: String? = null,

        var email: String? = null,
        var picture: String? = null,
        var others: Map<String, String> = HashMap()
)

@Document
data class User(
        @Id
        var id: String? = null,
        var identity: Identity? = null

)

interface IdentityRepository : MongoRepository<Identity, String> {

}

interface UserRepository : MongoRepository<User, String> {

}