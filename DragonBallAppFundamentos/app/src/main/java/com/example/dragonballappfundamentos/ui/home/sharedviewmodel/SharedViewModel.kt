package com.example.dragonballappfundamentos.ui.home.sharedviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballappfundamentos.data.local.SharedPreferencesService
import com.example.dragonballappfundamentos.data.network.APIClient
import com.example.dragonballappfundamentos.ui.home.HomeViewState
import com.example.dragonballappfundamentos.ui.home.characters.model.Character
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedViewModel: ViewModel() {

    private val apiClient = APIClient()

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading(true))
    val viewState: StateFlow<HomeViewState> = _viewState

    val charactersRaw: String
        get() = Gson().toJson(_characters.value)

    fun onViewAppearWithoutDataSaved(token: String) {
        _viewState.value = HomeViewState.Loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val charactersReceived: List<Character> = apiClient.getCharacters(token)
            Log.i("SALVA", "$charactersReceived")
            if (charactersReceived.isNotEmpty()) {
                _characters.value = charactersReceived
                _viewState.value = HomeViewState.Loading(false)
            } else {
                Log.i("SALVA", "Error por aquí")
                _viewState.value = HomeViewState.Error("Error con el servidor en la obtención de datos.")
            }
        }
    }

    fun onViewAppearWithDataSaved(characters: String) {
        _viewState.value = HomeViewState.Loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val charactersSavedArray: Array<Character> = Gson().fromJson(characters, Array<Character>::class.java)
            _characters.value = charactersSavedArray.toList()
            _viewState.value = HomeViewState.Loading(false)
        }
    }
}