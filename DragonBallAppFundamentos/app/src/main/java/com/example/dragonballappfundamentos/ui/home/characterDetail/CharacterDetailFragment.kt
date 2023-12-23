package com.example.dragonballappfundamentos.ui.home.characterDetail

import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.dragonballappfundamentos.R
import com.example.dragonballappfundamentos.databinding.FragmentCharacterDetailBinding
import com.example.dragonballappfundamentos.databinding.FragmentCharactersBinding
import com.example.dragonballappfundamentos.domain.interfaces.OnBackPressedListenerCharacterDetail
import com.example.dragonballappfundamentos.ui.home.characters.model.Character
import com.example.dragonballappfundamentos.ui.home.sharedviewmodel.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CharacterDetailFragment : Fragment(), OnBackPressedListenerCharacterDetail {

    companion object {
        private const val ARG_CHARACTER_POSITION = "characterPosition"

        fun newInstance(characterPosition: Int): CharacterDetailFragment {
            val fragment = CharacterDetailFragment()
            val args = Bundle()
            args.putInt(ARG_CHARACTER_POSITION, characterPosition)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: FragmentCharacterDetailBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initComponents()
        initListeners()
        initObservers()
    }

    private fun initComponents() {
        arguments?.getInt(ARG_CHARACTER_POSITION).let {
            it?.let { characterPosition ->
                val character: Character = sharedViewModel.characters.value[characterPosition]
                Glide
                    .with(binding.root)
                    .load(character.photo)
                    .centerCrop()
                    .placeholder(R.drawable.ic_insert_photo)
                    .into(binding.ivCharacterDetail)
                binding.tvCharacterHPStats.text = "${character.currentLife}/${character.maxLife}"
                binding.tvCharacterDetailName.text = "${character.name}"
                binding.pbCharacterDetailLife.progress = character.currentLife
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch(Dispatchers.Main) {
            sharedViewModel.characters.collect { characters ->
                updateUI()
            }
        }
    }

    private fun updateUI() {
        arguments?.getInt(ARG_CHARACTER_POSITION).let {
            it?.let { characterPosition ->
                val character: Character = sharedViewModel.characters.value[characterPosition]
                binding.tvCharacterHPStats.text = "${character.currentLife}/${character.maxLife}"
            }
        }
    }

    private fun initListeners() {
        binding.btnTimesSelected.setOnClickListener {
            showToast("Hola, este es un Toast sencillo en Android")
        }
        binding.fbHealCharacter.setOnClickListener {

        }
        binding.fbHitCharacter.setOnClickListener {

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        Log.i("SALVA", "PULSANDO ATRÁS")
        if (requireActivity().supportFragmentManager.backStackEntryCount > 0) {
            requireActivity().supportFragmentManager.popBackStack()
        } else {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}