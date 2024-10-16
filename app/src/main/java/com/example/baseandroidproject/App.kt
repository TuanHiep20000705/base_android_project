package com.example.baseandroidproject

import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun changeLang(lang: String) {
        val resources: Resources = instance.resources
        val configuration: Configuration = resources.configuration
        val localeList = LocaleList(Locale(lang))
        LocaleList.setDefault(localeList)
        configuration.setLocales(localeList)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        instance.createConfigurationContext(configuration)
    }

    companion object {
        lateinit var instance: App
            private set
    }
}