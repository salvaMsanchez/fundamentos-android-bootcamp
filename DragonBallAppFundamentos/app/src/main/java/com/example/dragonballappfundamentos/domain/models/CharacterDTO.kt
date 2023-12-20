package com.example.dragonballappfundamentos.domain.models

data class CharacterDTO(
    val name: String,
    val photo: String,
    val description: String,
    val favorite: Boolean,
    val id: String,
)