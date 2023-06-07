package com.example.rentcars.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataCredentials @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun logout() {
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit {
            putString(SP_ARG_TOKEN, null)
        }
    }

    fun saveToken(token: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(SP_ARG_TOKEN, token).apply()
    }

    fun getToken(): String? {
        val prefs: SharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return prefs.getString(SP_ARG_TOKEN, null)
    }

    companion object {
        private const val SP_NAME = "com.teamforce.thanksapp"
        private const val SP_ARG_TOKEN = "Token"
    }
}