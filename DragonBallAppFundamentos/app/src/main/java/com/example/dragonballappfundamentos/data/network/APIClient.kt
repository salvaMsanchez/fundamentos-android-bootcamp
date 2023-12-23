package com.example.dragonballappfundamentos.data.network

import com.example.dragonballappfundamentos.domain.models.CharacterDTO
import com.example.dragonballappfundamentos.ui.home.characters.model.Character
import com.google.gson.Gson
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class APIClient() {
    // COMPANION OBJECT
    companion object {
        private const val BASE_URL = "https://dragonball.keepcoding.education/api/"
        private const val LOGIN_EXTENSION = "auth/login"
        private const val GET_CHARACTERS_EXTENSION = "heros/all"
    }

    // PROPERTIES
    private var token = ""

    // FUNCTIONS
    fun getToken(): String {
        return token
    }

    fun login(email: String, password: String): Boolean {
        val client = OkHttpClient()
        val url = "${Companion.BASE_URL}${LOGIN_EXTENSION}"
        val credentials = Credentials.basic(email, password)
        val formBody = FormBody.Builder()
            .build()
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", credentials)
            .post(formBody)
            .build()
        val call = client.newCall(request)
        val response = call.execute()
        return if (response.isSuccessful) {
            response.body?.let {
                token = it.string()
            }
            true
        } else {
            false
        }
    }

    fun getCharacters(token: String): List<Character> {
        val client = OkHttpClient()
        val url = "${BASE_URL}${GET_CHARACTERS_EXTENSION}"
        val formBody = FormBody.Builder()
            .add("name", "")
            .build()
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $token")
            .post(formBody)
            .build()
        val call = client.newCall(request)
        val response = call.execute()
        if (response.isSuccessful) {
            response.body?.let {
                val charactersDtoArray: Array<CharacterDTO> =
                    Gson().fromJson(it.string(), Array<CharacterDTO>::class.java)
                val characterArray = charactersDtoArray.map { characterDto ->
                    Character(characterDto.name, characterDto.photo, 100, 100, 0)
                }
                val characterMutableList = characterArray.toMutableList()
                characterMutableList.removeLast()
                return characterMutableList.toList()
            }
            return emptyList()
        } else {
            return emptyList()
        }
    }
}