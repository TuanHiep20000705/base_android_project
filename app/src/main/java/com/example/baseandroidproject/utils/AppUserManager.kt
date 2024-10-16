package com.example.baseandroidproject.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.baseandroidproject.App
import com.example.baseandroidproject.constants.LanguageApp

object AppUserManager {
    private const val PREFERENCE_NAME = "RECOVERY_PREFS"
    private const val KEY_LANGUAGE = "KEY_LANGUAGE"

    private val preference: SharedPreferences =
        App.instance.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    var languageApp: String
        get() {
            return preference.getString(KEY_LANGUAGE, LanguageApp.ENGLISH.countryCode)
                ?: LanguageApp.ENGLISH.countryCode
        }
        set(language) {
            preference.edit().putString(KEY_LANGUAGE, language).apply()
        }
}