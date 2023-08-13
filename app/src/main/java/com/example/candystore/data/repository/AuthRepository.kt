package com.example.candystore.data.repository

import com.example.candystore.data.UserPreferences
import com.example.candystore.data.api.AuthRetrofitInstance
import com.example.candystore.data.models.UserAuth

class AuthRepository(
    private val preferences: UserPreferences
) : BaseRepository(){

    suspend fun validateUser(token: String) =
        AuthRetrofitInstance.authApi.checkUser(token)

    suspend fun login(userAuth: UserAuth) = safeApiCall {
        AuthRetrofitInstance.authApi.login(userAuth)
    }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }


}