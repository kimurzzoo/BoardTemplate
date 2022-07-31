package com.templete.board.account.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import java.security.MessageDigest
import java.util.*
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.xml.bind.DatatypeConverter

class JwtAuthenticationFilter(private val jwtTokenProvider: JwtTokenProvider) : GenericFilterBean() {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        // 헤더에서 JWT 를 받아옵니다.
        /*
        var userFingerPrint : String? = null
        var rawcookies = (request as HttpServletRequest).cookies
        if(rawcookies != null && rawcookies.size > 0)
        {
            var cookies = Arrays.stream(rawcookies).collect(Collectors.toList())
            var cookie = cookies.stream().filter {
                "__Secure-Fgp".equals(it.name)
            }.findFirst()
            if(cookie.isPresent)
            {
                userFingerPrint = cookie.get().value
            }
        }

        var digest = MessageDigest.getInstance("SHA-256")
        var userFingerPrintDigest = digest.digest(userFingerPrint?.toByteArray())
        var userFingerPrintHash = DatatypeConverter.printHexBinary(userFingerPrintDigest)*/

        val token: String? = jwtTokenProvider.resolveToken((request as HttpServletRequest))
        // 유효한 토큰인지 확인합니다.
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
            val authentication = jwtTokenProvider.getAuthentication(token)
            // SecurityContext 에 Authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().authentication = authentication
        }
        chain.doFilter(request, response)
    }
}