package com.example.dragonballappfundamentos.ui.characters.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dragonballappfundamentos.databinding.ItemCharacterBinding
import com.example.dragonballappfundamentos.ui.characters.model.Character

class CharactersViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var binding = ItemCharacterBinding.bind(view)

    fun bind(character: Character, onItemSelected: (String) -> Unit) {

    }
}