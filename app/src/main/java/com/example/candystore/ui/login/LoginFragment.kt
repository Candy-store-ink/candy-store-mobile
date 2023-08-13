package com.example.candystore.ui.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.candystore.data.api.AuthApi
import com.example.candystore.data.api.UserApi
import com.example.candystore.data.models.UserAuth
import com.example.candystore.data.repository.AuthRepository
import com.example.candystore.data.repository.UserRepository
import com.example.candystore.databinding.FragmentLoginBinding
import com.example.candystore.ui.base.BaseFragment
import com.example.candystore.ui.enable
import com.example.candystore.ui.viewmodels.AuthViewModel
import com.example.candystore.ui.visible
import com.example.candystore.utils.Resource

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visible(false)
        binding.singInBtn.enable(false)
        viewModel.authResponse.observe(viewLifecycleOwner) { response ->
            binding.progressBar.visible(false)
            when (response) {
                is Resource.Success -> {
                    viewModel.saveAuthToken(response.data.token)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error login", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading ->{}
            }
        }

        binding.passwordInputEditText.addTextChangedListener {
            val email = binding.loginInputEditText.text.toString().trim()
            //@todo add internet check
            binding.singInBtn.enable(Patterns.EMAIL_ADDRESS.matcher(email).matches())

        }

        binding.singInBtn.setOnClickListener {
            val email = binding.loginInputEditText.text.toString().trim()
            val password = binding.passwordInputEditText.text.toString().trim()
            val userAuth = UserAuth(email, password)
            binding.progressBar.visible(true)
            viewModel.login(userAuth)

        }
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

}