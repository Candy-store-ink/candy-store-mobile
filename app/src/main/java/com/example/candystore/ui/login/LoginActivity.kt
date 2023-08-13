package com.example.candystore.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.candystore.data.UserPreferences
import com.example.candystore.data.api.AuthApi
import com.example.candystore.data.api.BaseRetrofitInstance
import com.example.candystore.data.repository.AuthRepository
import com.example.candystore.databinding.ActivityLoginBinding
import com.example.candystore.ui.productspage.ProductsActivity
import com.example.candystore.ui.startNewActivity
import com.example.candystore.ui.viewmodels.AuthViewModel
import com.example.candystore.ui.viewmodels.ViewModelProviderFactory
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    lateinit var viewModel: AuthViewModel
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root

        val authRepository = AuthRepository(
            BaseRetrofitInstance().buildApi(AuthApi::class.java),
            UserPreferences(this)
        )
        val viewModelProviderFactory = ViewModelProviderFactory(authRepository)
        viewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        )[AuthViewModel::class.java]

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        setContentView(view)


        val userPreferences = UserPreferences(this)

        lifecycleScope.launch {
            userPreferences.authToken.collect {token ->
                if (token != null) startNewActivity(ProductsActivity::class.java)
                //Toast.makeText(this@LoginActivity, token ?: "Token is null", Toast.LENGTH_SHORT).show()

            }
        }

    }
}