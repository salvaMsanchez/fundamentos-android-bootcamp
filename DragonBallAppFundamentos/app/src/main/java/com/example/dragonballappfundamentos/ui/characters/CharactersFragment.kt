package com.example.dragonballappfundamentos.ui.characters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dragonballappfundamentos.R
import com.example.dragonballappfundamentos.data.local.SharedPreferencesService
import com.example.dragonballappfundamentos.databinding.FragmentCharactersBinding
import com.example.dragonballappfundamentos.ui.characters.adapter.CharactersAdapter

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding

    private lateinit var adapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("Salva", "El token recuperado de SharedPreferences es ${SharedPreferencesService.getToken(binding.root.context)}")

        initUI()
    }

    private fun initUI() {
        initListeners()
        initObservers()
        configureRecyclerView()
    }

    private fun configureRecyclerView() {
        adapter = CharactersAdapter { characterName -> goToDetail(characterName) }
        binding.rvCharacters.setHasFixedSize(true)
        binding.rvCharacters.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvCharacters.adapter = adapter
    }

    private fun initObservers() {

    }

    private fun initListeners() {

    }

    private fun goToDetail(characterName: String) {

    }
}