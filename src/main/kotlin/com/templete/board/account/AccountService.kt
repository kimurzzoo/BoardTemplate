package com.templete.board.account

import com.templete.board.account.security.JwtTokenProvider
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Update
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService(private val passwordEncoder : PasswordEncoder,
                     private val authenticationManager : AuthenticationManager,
                     private val jwtTokenProvider: JwtTokenProvider,
                     private val userRepository: UserRepository,
                     private val refreshTokenRepository: RefreshTokenRepository,
                     private val mongoTemplate: MongoTemplate) {

    fun login(loginDTO: LoginDTO) : TokenDTO
    {
        try
        {
            println("로그인 중")
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginDTO.email, loginDTO.password, null)
            )
        }
        catch (e: BadCredentialsException) {
            throw BadCredentialsException("로그인 실패")
        }

        var user = userRepository.findByEmail(loginDTO.email)
        val accessToken = jwtTokenProvider.createToken(loginDTO.email, user.roles)
        val refreshToken = jwtTokenProvider.createRefreshToken()
        val refreshTokenIns = RefreshToken(null, user._id!!, refreshToken)

        mongoTemplate.save(refreshTokenIns)
        mongoTemplate.update(User::class.java)
            .matching(where("_id").`is`(user._id))
            .apply(Update().set("refreshToken", refreshTokenIns))
            .first()

        return TokenDTO(accessToken, refreshToken)
    }

    fun register(registerDTO: RegisterDTO) : RegisterResultDTO
    {
        val user = User(null, registerDTO.nickname, registerDTO.email, registerDTO.password, true, mutableListOf("ROLE_USER"))
        mongoTemplate.save(user)

        var registerResultDTO = RegisterResultDTO(true, "good register")
        return registerResultDTO
    }
}