package com.example.candystore.models

data class UserAuthResponse(
    val token: String,
    val tokenType: String = "Bearer",
    val user: User,
)
