package com.example.dragonballappfundamentos.ui.home.characters.model

data class Character(
    val name: String,
    val photo: String,
    var maxLife: Int,
    var currentLife: Int,
    var timesSelected: Int,
)