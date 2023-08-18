package com.example.candystore.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.candystore.data.UserPreferences
import com.example.candystore.ui.login.LoginActivity
import com.example.candystore.ui.productspage.ProductsActivity
import com.example.candystore.ui.viewmodels.SplashViewModel
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val viewModel = SplashViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {

                viewModel.isLoading.value
            }
        }
        checkToken()
    }

    private fun checkToken() {
        val userPreferences = UserPreferences(this)
        lifecycleScope.launch {
            userPreferences.authToken.collect { token ->
                if (viewModel.isToken(token)) {
                    startNewActivity(ProductsActivity::class.java)
                } else {
                    startNewActivity(LoginActivity::class.java)
                }
            }
        }
    }
}