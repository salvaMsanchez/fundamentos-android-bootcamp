package com.example.dragonballappfundamentos.ui.characters.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dragonballappfundamentos.R
import com.example.dragonballappfundamentos.databinding.ItemCharacterBinding
import com.example.dragonballappfundamentos.ui.characters.model.Character

class CharactersViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var binding = ItemCharacterBinding.bind(view)

    fun bind(character: Character, onItemSelected: (String) -> Unit) {
        binding.tvCharacterName.text = character.name
        Glide
            .with(binding.root)
            .load(character.photo)
            .centerCrop()
            .placeholder(R.drawable.ic_insert_photo)
            .into(binding.ivCharacter)
        binding.root.setOnClickListener { onItemSelected(character.name) }
    }
}