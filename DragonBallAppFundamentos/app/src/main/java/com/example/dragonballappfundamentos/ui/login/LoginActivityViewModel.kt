package com.example.dragonballappfundamentos.ui.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballappfundamentos.data.network.APIClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginActivityViewModel(): ViewModel() {
    // COMPANION OBJECT
    private companion object {
        const val MIN_PASSWORD_LENGTH = 3
    }

    // API CLIENT
    private val apiClient = APIClient()

    // STATES
    private val _viewState = MutableStateFlow<LoginViewState>(LoginViewState.Loading(false))
    val viewState: StateFlow<LoginViewState> = _viewState

    // FUNCTIONS
    fun onLoginSelected(email: String, password: String) {
        if (isValidEmail(email) && isValidPassword(password)) {
            loginUser(email, password)
        } else {
            onFieldsChanged(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        _viewState.value = LoginViewState.Loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            if (apiClient.login(email, password)) {
                _viewState.value = LoginViewState.AccessCompleted(apiClient.getToken())
                _viewState.value = LoginViewState.Loading(false)
            } else {
                _viewState.value = LoginViewState.Loading(false)
                _viewState.value = LoginViewState.Error("Autenticación fallida")
            }
        }
    }

    fun onFieldsChanged(email: String, password: String) {
        _viewState.value = LoginViewState.ValidCredentials(isValidEmail(email), isValidPassword(password))
    }

    private fun isValidEmail(email: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()

    private fun isValidPassword(password: String): Boolean =
        password.length >= MIN_PASSWORD_LENGTH || password.isEmpty()
}