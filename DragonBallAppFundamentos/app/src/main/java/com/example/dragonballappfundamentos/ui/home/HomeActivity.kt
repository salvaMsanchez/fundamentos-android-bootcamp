package com.example.dragonballappfundamentos.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.dragonballappfundamentos.R
import com.example.dragonballappfundamentos.databinding.ActivityHomeBinding
import com.example.dragonballappfundamentos.domain.interfaces.OnBackPressedListenerCharacterDetail
import com.example.dragonballappfundamentos.ui.home.characters.CharactersFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeActivity : AppCompatActivity() {
    // VIEW BINDING
    lateinit var binding: ActivityHomeBinding

    // LIFECYCLE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        showCharactersFragment()
    }

    // FUNCTIONS
    private val onBackPressedCallback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)

            if (currentFragment is OnBackPressedListenerCharacterDetail) {
                (currentFragment as OnBackPressedListenerCharacterDetail).onBackPressed()
            } else {
                showFinishDialog()
            }
        }
    }

    private fun showFinishDialog() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle("¿Estás seguro?")
            setMessage("¿Quieres abandonar la aplicación?")
            setPositiveButton("SÍ") { _, _ -> finish() }
            setNegativeButton("NO", null)
            show()
        }
    }

    private fun showCharactersFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(binding.fragmentContainerView.id, CharactersFragment())
            .commit()
    }
}