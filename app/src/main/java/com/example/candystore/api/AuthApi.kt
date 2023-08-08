package com.example.candystore.api

import com.example.candystore.models.UserAuth
import com.example.candystore.models.UserAuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun getUser(
        @Body userAuth: UserAuth
    ): UserAuthResponse
}