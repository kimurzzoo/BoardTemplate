package com.templete.board.account

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.and
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val passwordEncoder : PasswordEncoder,
                  private val authenticationManager : AuthenticationManager,
                  private val jwtTokenProvider: JwtTokenProvider,
                  private val userRepository: UserRepository,
                  private val mongoTemplate: MongoTemplate) {

    fun login(loginDTO: LoginDTO) : TokenDTO?
    {

        var criteria = Criteria("email").`is`(loginDTO.email)

        val query = Query(criteria)

        var user : User? = mongoTemplate.findOne(query, User::class.java, "user")
        if(user == null)
        {
            return null
        }

        if(!passwordEncoder.matches(loginDTO.password, user.m_password))
        {
            return null
        }

        val accessToken = jwtTokenProvider.createToken(loginDTO.email, user.roles)
        val refreshToken = jwtTokenProvider.createRefreshToken()
        val refreshTokenIns = RefreshToken(null, user, refreshToken)

        mongoTemplate.save(refreshTokenIns)
        mongoTemplate.update(User::class.java)
            .matching(where("_id").`is`(user._id))
            .apply(Update().set("refreshToken", refreshTokenIns))
            .first()

        return TokenDTO(accessToken, refreshToken)
    }

    fun register(registerDTO: RegisterDTO) : RegisterResultDTO
    {
        val user = User(registerDTO.nickname, registerDTO.email, passwordEncoder.encode(registerDTO.password), true, mutableListOf("ROLE_USER"))
        userRepository.save(user)

        var registerResultDTO = RegisterResultDTO(true, "good register")
        return registerResultDTO
    }
}