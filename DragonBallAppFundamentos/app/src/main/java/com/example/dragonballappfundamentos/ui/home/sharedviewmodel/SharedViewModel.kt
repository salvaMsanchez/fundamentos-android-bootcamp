package com.example.dragonballappfundamentos.ui.home.sharedviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballappfundamentos.data.network.APIClient
import com.example.dragonballappfundamentos.ui.home.HomeViewState
import com.example.dragonballappfundamentos.ui.home.characters.model.Character
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class SharedViewModel: ViewModel() {
    // API CLIENT
    private val apiClient = APIClient()

    // STATES
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private val _characterDefeated = MutableStateFlow<Boolean>(false)
    val characterDefeated: StateFlow<Boolean> = _characterDefeated

    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading(true))
    val viewState: StateFlow<HomeViewState> = _viewState

    // PROPERTIES
    val charactersRaw: String
        get() = Gson().toJson(_characters.value)

    // FUNCTIONS
    fun onViewAppearWithoutDataSaved(token: String) {
        _viewState.value = HomeViewState.Loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val charactersReceived: List<Character> = apiClient.getCharacters(token)
            if (charactersReceived.isNotEmpty()) {
                updateCharacters(charactersReceived)
                _viewState.value = HomeViewState.Loading(false)
            } else {
                _viewState.value = HomeViewState.Error("Error con el servidor en la obtención de datos.")
            }
        }
    }

    fun onViewAppearWithDataSaved(characters: String) {
        _viewState.value = HomeViewState.Loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val charactersSavedArray: Array<Character> = Gson().fromJson(characters, Array<Character>::class.java)
            updateCharacters(charactersSavedArray.toList())
            _viewState.value = HomeViewState.Loading(false)
        }
    }

    fun updateCharacters(newCharacters: List<Character>) {
        _characters.value = newCharacters
    }

    fun onHealButtonPressed(characterPosition: Int) {
        val characterSelected: Character = _characters.value[characterPosition]
        when {
            characterSelected.currentLife + 20 > 100 -> characterLifeGreaterThan100WhenHealing(characterPosition, characterSelected)
            else -> increasedCharacterLife(characterPosition, characterSelected)
        }
    }

    private fun characterLifeGreaterThan100WhenHealing(characterPosition: Int, characterSelected: Character) {
        val characterUpdated: Character = Character(characterSelected.name, characterSelected.photo, characterSelected.maxLife, 100, characterSelected.timesSelected)
        _characters.value = _characters.value.toMutableList().apply {
            if (characterPosition in 0 until size) {
                set(characterPosition, characterUpdated)
            }
        }
    }

    private fun increasedCharacterLife(characterPosition: Int, characterSelected: Character) {
        val characterUpdated: Character = Character(characterSelected.name, characterSelected.photo, characterSelected.maxLife, characterSelected.currentLife + 20, characterSelected.timesSelected)
        _characters.value = _characters.value.toMutableList().apply {
            if (characterPosition in 0 until size) {
                set(characterPosition, characterUpdated)
            }
        }
    }

    fun onHitButtonPressed(characterPosition: Int) {
        val characterSelected: Character = _characters.value[characterPosition]
        val damage: Int = Random.nextInt(10, 61)
        when {
            characterSelected.currentLife - damage <= 0 -> characterDefeated(characterPosition, characterSelected)
            else -> characterInjured(characterPosition, characterSelected, damage)
        }
    }

    private fun characterDefeated(characterPosition: Int, characterSelected: Character) {
        val characterUpdated: Character = Character(characterSelected.name, characterSelected.photo, characterSelected.maxLife, 0, characterSelected.timesSelected)
        _characters.value = _characters.value.toMutableList().apply {
            if (characterPosition in 0 until size) {
                set(characterPosition, characterUpdated)
                _characterDefeated.value = true
            }
        }
    }

    private fun characterInjured(characterPosition: Int, characterSelected: Character, damage: Int) {
        val characterUpdated: Character = Character(characterSelected.name, characterSelected.photo, characterSelected.maxLife, characterSelected.currentLife - damage, characterSelected.timesSelected)
        _characters.value = _characters.value.toMutableList().apply {
            if (characterPosition in 0 until size) {
                set(characterPosition, characterUpdated)
            }
        }
    }

    fun onRecoverLifeButtonPressed() {
        _characters.value = _characters.value.map { character ->
            Character(character.name, character.photo, character.maxLife, 100, character.timesSelected)
        }
    }

    fun addCharacterTimesSelected(characterPosition: Int) {
        val characterSelected: Character = _characters.value[characterPosition]
        val characterUpdated: Character = Character(characterSelected.name, characterSelected.photo, characterSelected.maxLife, characterSelected.currentLife, characterSelected.timesSelected + 1)
        _characters.value = _characters.value.toMutableList().apply {
            if (characterPosition in 0 until size) {
                set(characterPosition, characterUpdated)
            }
        }
    }

    fun setCharacterDefeatedToFalse() {
        _characterDefeated.value = false
    }
}