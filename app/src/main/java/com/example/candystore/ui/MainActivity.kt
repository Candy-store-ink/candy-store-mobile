package com.example.candystore.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.candystore.R
import com.example.candystore.data.UserPreferences
import com.example.candystore.data.repository.AuthRepository
import com.example.candystore.databinding.ActivityMainBinding
import com.example.candystore.ui.viewmodels.AuthViewModel
import com.example.candystore.ui.viewmodels.ViewModelProviderFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: AuthViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        val authRepository = AuthRepository()
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
                Toast.makeText(this@MainActivity, token ?: "Token is null", Toast.LENGTH_SHORT).show()

            }
        }

    }
}