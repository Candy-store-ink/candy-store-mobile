package com.example.candystore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.candystore.data.models.UserAuth
import com.example.candystore.data.repository.AuthRepository
import com.example.candystore.databinding.FragmentLoginBinding
import com.example.candystore.ui.base.BaseFragment
import com.example.candystore.ui.viewmodels.AuthViewModel
import com.example.candystore.utils.Resource

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.authResponse.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error login", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.singInBtn.setOnClickListener {
            val email = binding.loginInputEditText.text.toString().trim()
            val password = binding.passwordInputEditText.text.toString().trim()
            val userAuth = UserAuth(email, password)
            //@todo add input validation
            viewModel.login(userAuth)
        }
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository()

}