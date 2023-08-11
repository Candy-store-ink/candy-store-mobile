package com.example.candystore.data.repository

import com.example.candystore.data.api.AuthRetrofitInstance
import com.example.candystore.data.models.UserAuth

class AuthRepository {

    suspend fun validateUser(token: String) =
        AuthRetrofitInstance.authApi.checkUser(token)

    suspend fun getUser(userAuth: UserAuth) =
        AuthRetrofitInstance.authApi.getUser(userAuth)
}