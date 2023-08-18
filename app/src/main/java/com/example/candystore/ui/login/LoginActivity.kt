package com.example.candystore.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.candystore.R
import com.example.candystore.data.UserPreferences
import com.example.candystore.ui.productspage.ProductsActivity
import com.example.candystore.ui.startNewActivity
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userPreferences = UserPreferences(this)

        lifecycleScope.launch {
            userPreferences.authToken.collect { token ->
                if (token != null) {
                    startNewActivity(ProductsActivity::class.java)
                }
            }
        }
    }
}