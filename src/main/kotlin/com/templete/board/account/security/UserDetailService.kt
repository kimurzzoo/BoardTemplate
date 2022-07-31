package com.templete.board.account.security

import com.templete.board.account.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class UserDetailService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username : String) : UserDetails
    {
        return userRepository.findByEmail(username) ?: throw UsernameNotFoundException("존재하지 않는 username 입니다.")
    }

}