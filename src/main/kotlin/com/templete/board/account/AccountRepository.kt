package com.templete.board.account

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String> {
    fun findByEmail(email : String) : User
}

@Repository
interface RefreshTokenRepository : MongoRepository<RefreshToken, String>
{

}