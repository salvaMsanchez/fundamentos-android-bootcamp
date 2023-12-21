package com.example.dragonballappfundamentos.ui.home

sealed class HomeViewState {
    class Error(val errorMessage: String): HomeViewState()
    class Loading(val loading: Boolean): HomeViewState()
}