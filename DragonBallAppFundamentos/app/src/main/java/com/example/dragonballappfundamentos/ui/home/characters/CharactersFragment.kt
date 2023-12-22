package com.example.dragonballappfundamentos.ui.home.characters

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dragonballappfundamentos.data.local.SharedPreferencesService
import com.example.dragonballappfundamentos.databinding.FragmentCharactersBinding
import com.example.dragonballappfundamentos.ui.home.HomeActivity
import com.example.dragonballappfundamentos.ui.home.HomeViewState
import com.example.dragonballappfundamentos.ui.home.characters.adapter.CharactersAdapter
import com.example.dragonballappfundamentos.ui.home.characters.model.Character
import com.example.dragonballappfundamentos.ui.home.sharedviewmodel.SharedViewModel
import com.example.dragonballappfundamentos.ui.login.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        if (SharedPreferencesService.getCharacters(binding.root.context) != "No Characters") {
            Log.i("Salva", "Datos recuperados desde SharedPreferences")
            sharedViewModel.onViewAppearWithDataSaved(SharedPreferencesService.getCharacters(binding.root.context))
        } else {
            Log.i("Salva", "Datos recuperados desde la API")
            sharedViewModel.onViewAppearWithoutDataSaved(SharedPreferencesService.getToken(binding.root.context))
        }
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
            setMessage("Lo siento. No se han podido recuperar los datos desde el servidor. Reinicia la aplicación o vuelva a entrar en otro momento.")
            setPositiveButton("Ok", null)
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
            // Todooooo!!
        }
        binding.ivLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(binding.root.context).apply {
            setTitle("¿Estás seguro?")
            setMessage("¿Quieres cerrar la sesión en la aplicación?")
            setPositiveButton("Sí") { _, _ -> logout() }
            setNegativeButton("No", null)
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

    private fun goToDetail(characterName: String) {

    }
}