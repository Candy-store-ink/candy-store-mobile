package com.example.candystore.data.repository

import com.example.candystore.data.api.AuthRetrofitInstance
import com.example.candystore.data.models.UserAuth

class AuthRepository : BaseRepository(){

    suspend fun validateUser(token: String) =
        AuthRetrofitInstance.authApi.checkUser(token)

    suspend fun login(userAuth: UserAuth) = safeApiCall {
        AuthRetrofitInstance.authApi.login(userAuth)
    }
}