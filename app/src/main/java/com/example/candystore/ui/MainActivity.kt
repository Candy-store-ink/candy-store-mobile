package com.example.candystore.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.candystore.R
import com.example.candystore.data.repository.AuthRepository
import com.example.candystore.ui.viewmodels.MainViewModel
import com.example.candystore.ui.viewmodels.MainViewModelProviderFactory

val Context.dataStore by preferencesDataStore("data_store")
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authRepository = AuthRepository()
        val mainViewModelProviderFactory = MainViewModelProviderFactory(authRepository)
        viewModel = ViewModelProvider(
            this,
            mainViewModelProviderFactory
        )[MainViewModel::class.java]

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        setContentView(R.layout.activity_login)

    }
}