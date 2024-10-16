package com.example.baseandroidproject.constants

import com.example.baseandroidproject.R
import com.example.baseandroidproject.data.model.Language

enum class LanguageApp(
    val flag: Int,
    val countryName: String,
    val countryCode: String,
) {
    ENGLISH(flag = R.drawable.img_flag_english, countryName = "English", countryCode = "en"),
    SPANISH(flag = R.drawable.img_flag_spanish, countryName = "Spanish", "es"),
    FRENCH(flag = R.drawable.img_flag_french, countryName = "French", "fr"),
    HINDI(flag = R.drawable.img_flag_hindi, countryName = "Hindi", "hi"),
    PORTUGUESE(flag = R.drawable.img_flag_portugeese, countryName = "Portuguese", "pt"),
    ;

    companion object {
        fun getLanguageApp(): List<Language> =
            LanguageApp.values().map {
                Language(
                    flag = it.flag,
                    countryName = it.countryName,
                    countryCode = it.countryCode,
                )
            }

        fun getCountryName(code: String): String =
            when (code) {
                SPANISH.countryCode -> SPANISH.countryName
                FRENCH.countryCode -> FRENCH.countryName
                HINDI.countryCode -> HINDI.countryName
                PORTUGUESE.countryCode -> PORTUGUESE.countryName
                else -> ENGLISH.countryName
            }
    }
}