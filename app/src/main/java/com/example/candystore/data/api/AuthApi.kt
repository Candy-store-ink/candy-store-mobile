package com.example.candystore.data.api

import com.example.candystore.data.models.UserAuth
import com.example.candystore.data.models.AuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login(
        @Body body: UserAuth
    ): AuthResponse
}