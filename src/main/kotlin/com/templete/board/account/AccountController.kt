package com.templete.board.account

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AccountController(private val userService : UserService) {
    @PostMapping("/login")
    fun login(@RequestBody loginDTO : LoginDTO) : ResponseEntity<*>
    {
        return ResponseEntity.ok().body(userService.login(loginDTO))
    }

    @PostMapping("/register")
    fun register(@RequestBody registerDTO : RegisterDTO) : ResponseEntity<*>
    {
        return ResponseEntity.ok().body(userService.register(registerDTO))
    }
}