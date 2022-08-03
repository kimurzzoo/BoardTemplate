package com.templete.board.account

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import javax.transaction.Transactional


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AccountRegisterTest() {
    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val mongoTemplate : MongoTemplate? = null

    @Autowired
    private val userRepository : UserRepository? = null

    @Test
    @Order(1)
    fun readTest()
    {
        /*var criteria = Criteria.where("email").`is`("kimurzzoo@gmail.com")
        val query = Query(criteria)

        var user : User? = mongoTemplate!!.findOne(query, User::class.java, "user")*/

        var registerDTO = RegisterDTO("kimurzzoo@gmail.com", "kimurzzoo","A1s2d3f4g%", "A1s2d3f4g%")
        userService!!.register(registerDTO)

        var user : User? = userRepository!!.findByEmail("kimurzzoo@gmail.com")
        assertEquals("kimurzzoo", user!!.nickname)

        userRepository.deleteAll()
    }
}