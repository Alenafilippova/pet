package com.example.myapplicat.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.myapplicat.Pet

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val PREFS_NAME = "PetCarePrefs"
        private const val KEY_PETS = "pets"
        private const val KEY_DARK_THEME = "dark_theme"
    }

    fun savePets(pets: List<Pet>) {
        val json = gson.toJson(pets)
        sharedPreferences.edit().putString(KEY_PETS, json).apply()
    }

    fun getPets(): List<Pet> {
        val json = sharedPreferences.getString(KEY_PETS, null)
        return if (json != null) {
            val type = object : TypeToken<List<Pet>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun setDarkTheme(enabled: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_DARK_THEME, enabled).apply()
    }

    fun isDarkThemeEnabled(): Boolean {
        return sharedPreferences.getBoolean(KEY_DARK_THEME, false)
    }
} 