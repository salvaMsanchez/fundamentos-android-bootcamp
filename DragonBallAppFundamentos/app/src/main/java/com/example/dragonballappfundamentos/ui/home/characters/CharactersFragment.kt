package com.example.dragonballappfundamentos.ui.home.characters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dragonballappfundamentos.data.local.SharedPreferencesService
import com.example.dragonballappfundamentos.databinding.FragmentCharactersBinding
import com.example.dragonballappfundamentos.ui.home.HomeViewState
import com.example.dragonballappfundamentos.ui.home.characters.adapter.CharactersAdapter
import com.example.dragonballappfundamentos.ui.home.characters.model.Character
import com.example.dragonballappfundamentos.ui.home.sharedviewmodel.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding

    private lateinit var adapter: CharactersAdapter

    private val sharedViewModel: SharedViewModel by activityViewModels()

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

        sharedViewModel.onViewAppear(SharedPreferencesService.getToken(binding.root.context))
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
        lifecycleScope.launch(Dispatchers.Main) {
            sharedViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
        lifecycleScope.launch(Dispatchers.Main) {
            sharedViewModel.characters.collect { characters ->
                updateAdapter(characters)
            }
        }
    }

    private fun updateAdapter(characters: List<Character>) {
        adapter.updateList(characters)
    }

    private fun updateUI(viewState: HomeViewState) {
        when(viewState) {
            is HomeViewState.Error -> Log.i("SALVA", "HomeViewState.Error")
            is HomeViewState.Loading -> Log.i("SALVA", "HomeViewState.Loading")
        }
    }

    private fun initListeners() {

    }

    private fun goToDetail(characterName: String) {

    }
}