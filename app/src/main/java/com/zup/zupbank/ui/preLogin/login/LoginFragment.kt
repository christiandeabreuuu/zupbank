package com.zup.zupbank.ui.preLogin.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.zup.zupbank.R
import com.zup.zupbank.common.Resource
import com.zup.zupbank.common.extension.*
import com.zup.zupbank.databinding.FragmentLoginBinding
import com.zup.zupbank.domain.model.User
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.loginFormState.launchAndCollectIn(viewLifecycleOwner) { loginFormState ->
            binding.login.isEnabled = loginFormState.isDataValid
            loginFormState.usernameError?.let {
                binding.username.error = it
            }
            loginFormState.passwordError?.let {
                binding.password.error = it
            }
        }

        loginViewModel.loginUiState.launchAndCollectIn(this) { uiState ->
            when (uiState) {
                is Resource.Initialize -> binding.loading.gone()
                is Resource.Loading -> binding.loading.visible()
                is Resource.Success -> successLogin(uiState.data)
                is Resource.Error -> showLoginFailed(uiState.error)
            }
        }
        this.events()
    }

    private fun events() {
        binding.username.doAfterTextChanged { handleDataChanged() }

        binding.password.doAfterTextChanged { handleDataChanged() }

        binding.password.actionDone {
            this.callLogin()
        }

        binding.login.setOnClickListener {
            this.callLogin()
        }
    }

    private fun callLogin() {
        loginViewModel.login(
            requireActivity(),
            binding.username.text.toString(),
            binding.password.text.toString()
        )
    }

    private fun handleDataChanged() {
        loginViewModel.loginDataChanged(
            binding.username.text.toString(),
            binding.password.text.toString()
        )
    }

    private fun successLogin(user: User?) {
        if (context == null || activity == null || user == null) {
            return
        }
    }

    private fun showLoginFailed(error: Throwable?) {
        context ?: return
        binding.loading.gone()
        toast(error?.message)
    }
}
