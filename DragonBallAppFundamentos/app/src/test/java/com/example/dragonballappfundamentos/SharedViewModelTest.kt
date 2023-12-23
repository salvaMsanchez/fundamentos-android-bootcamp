package com.example.dragonballappfundamentos

import com.example.dragonballappfundamentos.ui.home.characters.model.Character
import com.example.dragonballappfundamentos.ui.home.sharedviewmodel.SharedViewModel
import org.junit.Test

import org.junit.Assert.*

class SharedViewModelTest {

    val viewModel = SharedViewModel()

    val charactersList: List<Character> = listOf(
        Character("Goku", "photo", 100, 50, 4),
        Character("Trunks", "photo", 100, 90, 2),
        Character("Vegeta", "photo", 100, 33, 1),
        Character("Bulma", "photo", 100, 1, 3),
        Character("Kaito", "photo", 100, 87, 0),
    )

    @Test
    fun `Check parameter value change when character is defeated`() {
        viewModel.setCharacterDefeatedToFalse()
        assertFalse(viewModel.characterDefeated.value)
    }

    @Test
    fun `Check character healing - Increasing life 20`() {
        viewModel.updateCharacters(charactersList)
        val characterPosition = 0
        viewModel.onHealButtonPressed(characterPosition)
        assertEquals(70, viewModel.characters.value[characterPosition].currentLife)
    }

    @Test
    fun `Check character healing when life is greater than 100`() {
        viewModel.updateCharacters(charactersList)
        val characterPosition = 1
        viewModel.onHealButtonPressed(characterPosition)
        assertEquals(100, viewModel.characters.value[characterPosition].currentLife)
    }

    @Test
    fun `Check the damage to a character`() {
        viewModel.updateCharacters(charactersList)
        val characterPosition = 1
        val characterLifeBeforeDamage: Int = viewModel.characters.value[characterPosition].currentLife
        viewModel.onHitButtonPressed(characterPosition)
        assertTrue(viewModel.characters.value[characterPosition].currentLife < characterLifeBeforeDamage)
    }

    @Test
    fun `Check that the life does not drop below 0 when hit`() {
        viewModel.updateCharacters(charactersList)
        val characterPosition = 3
        val characterLifeBeforeDamage: Int = viewModel.characters.value[characterPosition].currentLife
        viewModel.onHitButtonPressed(characterPosition)
        assertTrue(viewModel.characters.value[characterPosition].currentLife < characterLifeBeforeDamage)
        assertEquals(0, viewModel.characters.value[characterPosition].currentLife)
    }
}