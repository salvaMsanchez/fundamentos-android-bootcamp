package com.example.dragonballappfundamentos.ui.home.characterDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.example.dragonballappfundamentos.R
import com.example.dragonballappfundamentos.databinding.FragmentCharacterDetailBinding
import com.example.dragonballappfundamentos.databinding.FragmentCharactersBinding
import com.example.dragonballappfundamentos.domain.interfaces.OnBackPressedListenerCharacterDetail
import com.example.dragonballappfundamentos.ui.home.sharedviewmodel.SharedViewModel

class CharacterDetailFragment : Fragment(), OnBackPressedListenerCharacterDetail {

    private lateinit var binding: FragmentCharacterDetailBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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