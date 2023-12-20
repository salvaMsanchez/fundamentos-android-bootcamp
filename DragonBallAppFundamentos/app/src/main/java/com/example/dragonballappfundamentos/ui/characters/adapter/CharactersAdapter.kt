package com.example.dragonballappfundamentos.ui.characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dragonballappfundamentos.R
import com.example.dragonballappfundamentos.ui.characters.model.Character

class CharactersAdapter(
    var characters: List<Character> = emptyList(),
    private val onItemSelected: (String) -> Unit
):
    RecyclerView.Adapter<CharactersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: CharactersViewHolder, position: Int) {
        viewHolder.bind(characters[position], onItemSelected)
    }

    override fun getItemCount(): Int = characters.size
}