package com.example.candystore.utils

import okhttp3.ResponseBody

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(
        val isNetworkError: Boolean,
        val errorCode: Int,
        val errorBody: ResponseBody
    ) : Resource<T>()
    class Loading<T> : Resource<T>()
}
