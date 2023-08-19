package com.example.candystore.ui.login

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.candystore.data.api.AuthApi
import com.example.candystore.data.models.UserAuth
import com.example.candystore.data.repository.AuthRepository
import com.example.candystore.databinding.FragmentLoginBinding
import com.example.candystore.ui.base.BaseFragment
import com.example.candystore.ui.enable
import com.example.candystore.ui.viewmodels.AuthViewModel
import com.example.candystore.ui.visible
import com.example.candystore.utils.Resource

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    private val PASS_MIN_CHARS = 5
    private var email: String = ""
    private var password: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visible(false)
        binding.singInBtn.enable(false)

        handleLoginNetworkResponse()
        enterEmail()
        enterPass()
        login()
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): AuthRepository {
        val api = baseRetrofitInstance.buildApi(AuthApi::class.java)
        return AuthRepository(api, userPreferences)
    }

    private fun login() {
        binding.singInBtn.setOnClickListener {
            val userAuth = UserAuth(email, password)
            binding.progressBar.visible(true)
            viewModel.login(userAuth)

        }
    }

    private fun handleLoginNetworkResponse() {
        viewModel.authResponse.observe(viewLifecycleOwner) { response ->
            binding.progressBar.visible(false)
            when (response) {
                is Resource.Success -> {
                    viewModel.saveAuthToken(response.data.token)
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error login", Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    binding.singInBtn.enable(false)
                }
            }
        }
    }
    private fun enterEmail() {
        binding.loginInputEditText.addTextChangedListener {
            val colorState: ColorStateList
            email = binding.loginInputEditText.text.toString().trim()
            //@todo add internet check
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                colorState = changeColorState(Color.GREEN)

            } else {
                colorState = changeColorState(Color.RED)
                binding.singInBtn.enable(false)
            }
            binding.textInputLayoutLogin.setBoxStrokeColorStateList(colorState)
        }
    }

    private fun enterPass() {
        binding.passwordInputEditText.addTextChangedListener {
            password = binding.passwordInputEditText.text.toString().trim()
            //@todo add internet check
            val colorState: ColorStateList
            if (password.length >= PASS_MIN_CHARS) {
                colorState = changeColorState(Color.GREEN)

                binding.singInBtn.enable(Patterns.EMAIL_ADDRESS.matcher(email).matches())

            } else {
                colorState = changeColorState(Color.RED)
                binding.singInBtn.enable(false)
            }

            binding.textInputLayoutPassword.setBoxStrokeColorStateList(colorState)
        }
    }

    private fun changeColorState(color: Int): ColorStateList {
        return ColorStateList(
            arrayOf(
                intArrayOf(R.attr.state_active),
                intArrayOf(R.attr.state_focused),
                intArrayOf(-R.attr.state_focused),
                intArrayOf(R.attr.state_hovered),
                intArrayOf(R.attr.state_enabled),
                intArrayOf(-R.attr.state_enabled)
            ),
            intArrayOf(
                color,
                color,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK
            )
        )
    }

}