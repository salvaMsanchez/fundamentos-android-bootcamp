package com.example.dragonballappfundamentos.data.network

import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class APIClient() {

    companion object {
        private const val BASE_URL = "https://dragonball.keepcoding.education/api/"
        private const val LOGIN_EXTENSION = "auth/login"
    }

    private var token = ""

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

}