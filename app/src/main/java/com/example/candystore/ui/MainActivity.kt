package com.example.candystore.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.candystore.R
import com.example.candystore.data.repository.AuthRepository
import com.example.candystore.databinding.ActivityMainBinding
import com.example.candystore.ui.viewmodels.AuthViewModel
import com.example.candystore.ui.viewmodels.ViewModelProviderFactory

val Context.dataStore by preferencesDataStore("data_store")
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

    }
}