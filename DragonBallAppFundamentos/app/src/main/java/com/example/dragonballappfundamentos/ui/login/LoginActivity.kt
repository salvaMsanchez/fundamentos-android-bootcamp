package com.example.dragonballappfundamentos.ui.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import com.example.dragonballappfundamentos.R
import com.example.dragonballappfundamentos.databinding.ActivityLoginBinding
import com.example.dragonballappfundamentos.domain.extensions.dismissKeyboard
import com.example.dragonballappfundamentos.domain.extensions.loseFocusAfterAction
import com.example.dragonballappfundamentos.domain.extensions.onTextChanged
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.dragonballappfundamentos.data.local.SharedPreferencesService
import com.example.dragonballappfundamentos.ui.home.HomeActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    val viewModel: LoginActivityViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (SharedPreferencesService.getToken(this) != "No Token") {
            Log.i("SALVA", "Navegando al Home directamente sin pasar por Login")
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Log.i("SALVA", "Navegando al Login")
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            initUI()
        }
    }

    private fun initUI() {
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    private fun controlCredentialsValidation(isValidEmail: Boolean, isValidPassword: Boolean) {
        binding.tiEmail.error = if (isValidEmail) null else getString(R.string.login_error_email)
        binding.tiPassword.error = if (isValidPassword) null else getString(R.string.login_error_password)

        val emailText = binding.tiEmail.text
        val passwordText = binding.tiPassword.text
        if (emailText != null && passwordText != null) {
            binding.btnLogin.isVisible = isValidEmail && isValidPassword && emailText.isNotEmpty() && passwordText.isNotEmpty()
        }


        //binding.tiEmail.error = if (viewState.isValidEmail) null else getString(R.string.login_error_email)
        //binding.tiPassword.error = if (viewState.isValidPassword) null else getString(R.string.login_error_password)
        //binding.pbLoginLoading.isVisible = viewState.isLoading
    }

    private fun updateUI(viewState: LoginViewState) {
        when (viewState) {
            is LoginViewState.Error -> loginError(viewState.errorMessage)
            is LoginViewState.Loading -> showLoading(viewState.loading)
            is LoginViewState.ValidCredentials -> controlCredentialsValidation(viewState.isValidEmail, viewState.isValidPassword)
            is LoginViewState.AccessCompleted -> navigateToHome(viewState.token)
        }
    }

    private fun loginError(errorMessage: String) {
        showAuthenticationFailedDialog(errorMessage)
    }

    private fun showAuthenticationFailedDialog(errorMessage: String) {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(errorMessage)
            setMessage("Las credenciales proporcionadas son incorrectas. Por favor, verifica tu correo electrónico y contraseña e inténtalo nuevamente.")
            setPositiveButton("Ok", null)
            show()
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.pbLoginLoading.isVisible = loading
    }

    private fun initListeners() {
        binding.tiEmail.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)
        binding.tiEmail.onTextChanged { onFieldChanged() }

        binding.tiPassword.loseFocusAfterAction(EditorInfo.IME_ACTION_DONE)
        binding.tiPassword.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
        binding.tiPassword.onTextChanged { onFieldChanged() }

        binding.btnLogin.setOnClickListener {
            it.dismissKeyboard()
            viewModel.onLoginSelected(
                binding.tiEmail.text.toString(),
                binding.tiPassword.text.toString()
            )
        }
    }

    private fun navigateToHome(token: String) {
        Log.i("SALVA", "El token es $token")
        SharedPreferencesService.saveToken(this, token = token)
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun onFieldChanged(hasFocus: Boolean = false) {
        if (!hasFocus) {
            viewModel.onFieldsChanged(
                binding.tiEmail.text.toString(),
                binding.tiPassword.text.toString()
            )
        }
    }

}