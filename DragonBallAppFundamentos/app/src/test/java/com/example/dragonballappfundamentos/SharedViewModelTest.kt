package com.example.dragonballappfundamentos

import com.example.dragonballappfundamentos.ui.home.characters.model.Character
import com.example.dragonballappfundamentos.ui.home.sharedviewmodel.SharedViewModel
import org.junit.Test

import org.junit.Assert.*

class SharedViewModelTest {

    val viewModel = SharedViewModel()

    val charactersList: List<Character> = listOf(
        Character("Goku", "photo", 100, 100, 4),
        Character("Trunks", "photo", 100, 90, 2),
        Character("Vegeta", "photo", 100, 33, 1),
        Character("Bulma", "photo", 100, 20, 3),
        Character("Kaito", "photo", 100, 87, 0),
    )

    @Test
    fun `Check parameter value change when character is defeated`() {
        viewModel.setCharacterDefeatedToFalse()
        assertFalse(viewModel.characterDefeated.value)
    }
}