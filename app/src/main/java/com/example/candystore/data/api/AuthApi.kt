package com.example.candystore.data.api

import com.example.candystore.data.models.UserAuth
import com.example.candystore.data.models.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {

    @GET("auth/token")
    suspend fun checkUser(
        @Query("token" ) token: String
    ) : AuthResponse

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") login: String,
        @Field("password") password: String
    ): AuthResponse
}