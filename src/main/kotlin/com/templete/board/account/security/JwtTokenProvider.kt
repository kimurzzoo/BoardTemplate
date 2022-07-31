package com.templete.board.account.security

import com.templete.board.account.security.util.RSAKey
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.io.File
import java.security.PrivateKey
import java.security.PublicKey
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

class JwtTokenProvider(private val userDetailsService : UserDetailService)
{
    private lateinit var secretKey : PrivateKey
    private lateinit var publicKey : PublicKey

    private val tokenValidTime = 30 * 60 * 1000L
    private val refreshTokenValidTime = 60 * 60 * 24 * 7 * 1000L

    @PostConstruct
    protected fun init()
    {
        val keyfile = File("/src/main/resources/jwtkey.pem")
        secretKey = RSAKey.readPrivateKey(keyfile)!!
        publicKey = RSAKey.readPublicKey(keyfile)!!
    }

    fun createToken(username : String, roles : MutableList<String>) : String //, userFingerPrintHash : String
    {
        val claims: Claims = Jwts.claims().setSubject(username) // JWT payload 에 저장되는 정보단위
        //claims["userFingerPrint"] = userFingerPrintHash // 정보는 key / value 쌍으로 저장된다.
        claims["roles"] = roles
        val now = Date()
        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("alg", "RS256")
            .setClaims(claims) // 정보 저장
            .setIssuedAt(now) // 토큰 발행 시간 정보
            .setExpiration(Date(now.time + tokenValidTime)) // set Expire Time
            .signWith(secretKey, SignatureAlgorithm.RS256)
            // signature 에 들어갈 secret값 세팅
            .compact()
    }

    fun createRefreshToken() : String
    {
        val now = Date()

        return Jwts.builder()
            .setExpiration(Date(now.time + refreshTokenValidTime))
            .signWith(secretKey, SignatureAlgorithm.RS256)
            .compact();
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token : String) : String
    {
        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token).body.subject
    }

    fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader("Authorization").substring("Bearer ".length)
    }

    fun validateToken(jwtToken: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(jwtToken)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }
}