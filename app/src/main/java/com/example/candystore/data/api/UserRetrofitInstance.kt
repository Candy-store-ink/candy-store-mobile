package com.example.candystore.data.api

import com.example.candystore.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRetrofitInstance {
    companion object {
        private val retrofit by lazy {

            val client = OkHttpClient.Builder()
                .build()

            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val userApi by lazy {
            retrofit.create(UserApi::class.java)
        }
    }
}