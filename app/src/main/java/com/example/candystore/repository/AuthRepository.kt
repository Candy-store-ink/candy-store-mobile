package com.example.candystore.repository

import com.example.candystore.api.AuthRetrofitInstance
import com.example.candystore.models.UserAuth

class AuthRepository {

    suspend fun validateUser(token: String) =
        AuthRetrofitInstance.authApi.checkUser(token)

    suspend fun getUser(userAuth: UserAuth) =
        AuthRetrofitInstance.authApi.getUser(userAuth)
}