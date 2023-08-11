package com.example.candystore.data.api

import com.example.candystore.data.models.UserAuth
import com.example.candystore.data.models.UserAuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {

    @GET("auth/token")
    suspend fun checkUser(
        @Query("token" ) token: String
    ) : Response<UserAuthResponse>

    @POST("auth/login")
    suspend fun getUser(
        @Body userAuth: UserAuth
    ): Response<UserAuthResponse>
}