package com.example.dragonballappfundamentos.ui.home.characters.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dragonballappfundamentos.R
import com.example.dragonballappfundamentos.databinding.ItemCharacterBinding
import com.example.dragonballappfundamentos.ui.home.characters.model.Character

class CharactersViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var binding = ItemCharacterBinding.bind(view)

    fun bind(character: Character, position: Int, onItemSelected: (Int) -> Unit) {
        binding.tvCharacterName.text = character.name
        binding.pbCharacterLife.progress = character.currentLife
        binding.tvCharacterHPStats.text = "${character.currentLife}/${character.maxLife}"
        Glide
            .with(binding.root)
            .load(character.photo)
            .centerCrop()
            .placeholder(R.drawable.ic_insert_photo)
            .into(binding.ivCharacter)
        binding.root.setOnClickListener { onItemSelected(position) }
    }
}