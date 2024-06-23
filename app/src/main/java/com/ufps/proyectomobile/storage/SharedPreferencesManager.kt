package com.ufps.proyectomobile.storage

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        const val PREFS_NAME = "user_prefs"
        const val KEY_EMAIL = "email"
    }

    fun saveLoginData(email: String) {
        prefs.edit().putString(KEY_EMAIL, email).apply()
    }

    fun getEmail(): String? {
        return prefs.getString(KEY_EMAIL, null)
    }


    fun clear() {
        prefs.edit().clear().apply()
    }
}
