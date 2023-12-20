package com.example.dragonballappfundamentos.ui.login

sealed class LoginViewState {
    class Error(val errorMessage: String): LoginViewState()
    class Loading(val loading: Boolean): LoginViewState()
    class ValidCredentials(val isValidEmail: Boolean, val isValidPassword: Boolean): LoginViewState()
    class AccessCompleted(val token: String): LoginViewState()
}