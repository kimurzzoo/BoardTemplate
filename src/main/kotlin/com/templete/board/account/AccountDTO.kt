package com.templete.board.account

data class LoginDTO(
    val email : String,
    val password : String
)

data class RegisterDTO(
    val email : String,
    val nickname : String,
    val password : String,
    val passwordConfirm : String
)

data class TokenDTO(
    val accessToken : String,
    val refreshToken : String
)

data class RegisterResultDTO(
    val ok : Boolean,
    val description : String
)