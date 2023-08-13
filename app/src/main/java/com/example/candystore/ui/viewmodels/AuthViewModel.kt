package com.example.candystore.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.candystore.data.models.UserAuth
import com.example.candystore.data.models.AuthResponse
import com.example.candystore.data.repository.AuthRepository
import com.example.candystore.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _authResponse: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val authResponse: LiveData<Resource<AuthResponse>>
        get() = _authResponse

    init {
        viewModelScope.launch {
            delay(5000)
            _isLoading.value = false
        }
    }

    fun login(userAuth: UserAuth) = viewModelScope.launch {
        _authResponse.postValue(authRepository.login(userAuth))

    }

    fun saveAuthToken(token: String) = viewModelScope.launch {
        authRepository.saveAuthToken(token)
    }




}