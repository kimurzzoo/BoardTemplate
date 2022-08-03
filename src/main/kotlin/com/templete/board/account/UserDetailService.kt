package com.templete.board.account

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailService(private val mongoTemplate: MongoTemplate) : UserDetailsService {

    override fun loadUserByUsername(username : String) : UserDetails
    {
        var criteria = Criteria("email")
        criteria.`is`(username)

        val query = Query(criteria)

        return mongoTemplate.findOne(query, User::class.java) ?: throw UsernameNotFoundException("존재하지 않는 username 입니다.")
    }

}