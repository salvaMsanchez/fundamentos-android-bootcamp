package com.example.dragonballappfundamentos.data.local

import android.content.Context

object SharedPreferencesService {

    private const val PREFS_NAME = "DragonBallFundamentosAndroid"
    private const val TOKEN_KEY = "tokenKey"
    private const val CHARACTERS_KEY = "charactersKey"

    fun getCharacters(context: Context, key: String = CHARACTERS_KEY, defaultValue: String = "No Characters"): String {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(key, defaultValue) ?: defaultValue
    }

    fun saveCharacters(context: Context, key: String = CHARACTERS_KEY, characters: String) {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString(key, characters)
            commit()
        }
    }

    fun deleteCharacters(context: Context, key: String = CHARACTERS_KEY) {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            remove(key)
            commit()
        }
    }

    fun getToken(context: Context, key: String = TOKEN_KEY, defaultValue: String = "No Token"): String {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(key, defaultValue) ?: defaultValue
    }

    fun saveToken(context: Context, key: String = TOKEN_KEY, token: String) {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString(key, token)
            commit()
        }
    }

    fun deleteToken(context: Context, key: String = TOKEN_KEY) {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            remove(key)
            commit()
        }
    }
}