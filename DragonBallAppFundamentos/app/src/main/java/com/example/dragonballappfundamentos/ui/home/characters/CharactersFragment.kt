package com.example.dragonballappfundamentos.ui.home.characters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dragonballappfundamentos.R
import com.example.dragonballappfundamentos.data.local.SharedPreferencesService
import com.example.dragonballappfundamentos.databinding.FragmentCharactersBinding
import com.example.dragonballappfundamentos.ui.home.HomeActivity
import com.example.dragonballappfundamentos.ui.home.HomeViewState
import com.example.dragonballappfundamentos.ui.home.characterDetail.CharacterDetailFragment
import com.example.dragonballappfundamentos.ui.home.characters.adapter.CharactersAdapter
import com.example.dragonballappfundamentos.ui.home.characters.model.Character
import com.example.dragonballappfundamentos.ui.home.sharedviewmodel.SharedViewModel
import com.example.dragonballappfundamentos.ui.login.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersFragment : Fragment() {
    // VIEW BINDING
    private lateinit var binding: FragmentCharactersBinding

    // RECYCLER VIEW - ADAPTER
    private lateinit var adapter: CharactersAdapter
    // VIEW MODEL
    private val sharedViewModel: SharedViewModel by activityViewModels()

    // LIFECYCLE
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        if (SharedPreferencesService.getCharacters(binding.root.context) != "No Characters") {
            sharedViewModel.onViewAppearWithDataSaved(SharedPreferencesService.getCharacters(binding.root.context))
        } else {
            sharedViewModel.onViewAppearWithoutDataSaved(SharedPreferencesService.getToken(binding.root.context))
        }
    }

    // FUNCTIONS
    private fun initUI() {
        initListeners()
        initObservers()
        configureRecyclerView()
    }

    private fun configureRecyclerView() {
        adapter = CharactersAdapter { characterPosition -> goToDetail(characterPosition) }
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
                SharedPreferencesService.saveCharacters(binding.root.context, characters = sharedViewModel.charactersRaw)
            }
        }
    }

    private fun updateAdapter(characters: List<Character>) {
        adapter.updateList(characters)
    }

    private fun updateUI(viewState: HomeViewState) {
        when(viewState) {
            is HomeViewState.Error -> loadCharactersError(viewState.errorMessage)
            is HomeViewState.Loading -> showLoading(viewState.loading)
        }
    }

    private fun loadCharactersError(errorMessage: String) {
        showLoadCharactersFailedDialog(errorMessage)
    }

    private fun showLoadCharactersFailedDialog(errorMessage: String) {
        MaterialAlertDialogBuilder(binding.root.context).apply {
            setTitle(errorMessage)
            setMessage(getString(R.string.load_characters_failed))
            setPositiveButton(getString(R.string.ok), null)
            show()
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.pbCharactersLoading.visibility = View.GONE
        binding.charactersToolbar.isVisible = !loading
        binding.rvCharacters.isVisible = !loading
    }

    private fun initListeners() {
        binding.ivRecoverLife.setOnClickListener {
            sharedViewModel.onRecoverLifeButtonPressed()
        }
        binding.ivLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(binding.root.context).apply {
            setTitle(getString(R.string.r_u_sure))
            setMessage(getString(R.string.close_app))
            setPositiveButton(getString(R.string.yes)) { _, _ -> logout() }
            setNegativeButton(getString(R.string.no), null)
            show()
        }
    }

    private fun logout() {
        removeDataSaved()
        goToLogin()
    }

    private fun removeDataSaved() {
        SharedPreferencesService.deleteCharacters(binding.root.context)
        SharedPreferencesService.deleteToken(binding.root.context)
    }

    private fun goToLogin() {
        val intent = Intent(binding.root.context, LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun goToDetail(characterPosition: Int) {
        val characterDetailFragment = CharacterDetailFragment.newInstance(characterPosition)
        val bindingHomeActivity = (requireActivity() as HomeActivity).binding

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(bindingHomeActivity.fragmentContainerView.id, characterDetailFragment)
            .addToBackStack(null)
            .commit()
    }
}