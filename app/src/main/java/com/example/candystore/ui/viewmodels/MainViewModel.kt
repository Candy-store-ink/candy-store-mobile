package com.example.candystore.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.candystore.data.models.UserAuth
import com.example.candystore.data.models.UserAuthResponse
import com.example.candystore.data.repository.AuthRepository
import com.example.candystore.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    val authRepository: AuthRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    val userAuthResponse: MutableLiveData<Resource<UserAuthResponse>> = MutableLiveData()

    init {
        viewModelScope.launch {
            validateUser("")
            delay(5000)
            _isLoading.value = false
        }
    }

    fun validateUser(token: String) = viewModelScope.launch {
        userAuthResponse.postValue(Resource.Loading())

        val response = authRepository.validateUser(token)
        userAuthResponse.postValue(handlingUserAuthResponse(response))

    }

    fun login(userAuth: UserAuth) = viewModelScope.launch {
        userAuthResponse.postValue(Resource.Loading())

        val response = authRepository.getUser(userAuth)
        userAuthResponse.postValue(handlingUserAuthResponse(response))
    }

    private fun handlingUserAuthResponse(
        response: Response<UserAuthResponse>
    ): Resource<UserAuthResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }



}