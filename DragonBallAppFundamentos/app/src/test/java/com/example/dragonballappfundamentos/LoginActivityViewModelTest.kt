package com.example.dragonballappfundamentos

import com.example.dragonballappfundamentos.ui.login.LoginActivityViewModel
import com.example.dragonballappfundamentos.ui.login.LoginViewState
import org.junit.Test

import org.junit.Assert.*

class LoginActivityViewModelTest {

    val viewModel = LoginActivityViewModel()

    @Test
    fun `Check password validity`() {
        val password1: String = "1a"
        val email1: String = "pepe"
        val password2: String = "1a3ok"
        val email2: String = "pepe@gmail.com"
        val password3: String = "P"
        val email3: String = "pepe@"
        val password4: String = ""
        val email4: String = ""
        val password5: String = "123"
        val email5: String = "pepe.com"

        viewModel.onFieldsChanged(email1, password1)
        assertEquals(LoginViewState.ValidCredentials(false, false), viewModel.viewState.value)
        viewModel.onFieldsChanged(email2, password2)
        assertEquals(LoginViewState.ValidCredentials(true, true), viewModel.viewState.value)
        viewModel.onFieldsChanged(email3, password3)
        assertEquals(LoginViewState.ValidCredentials(false, false), viewModel.viewState.value)
        viewModel.onFieldsChanged(email4, password4)
        assertEquals(LoginViewState.ValidCredentials(true, true), viewModel.viewState.value)
        viewModel.onFieldsChanged(email5, password5)
        assertEquals(LoginViewState.ValidCredentials(true, false), viewModel.viewState.value)
    }
}