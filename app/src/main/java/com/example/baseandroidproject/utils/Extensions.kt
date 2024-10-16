package com.example.baseandroidproject.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import android.view.View
import java.util.Locale

fun updateLocale(
    ctx: Context,
    localeToSwitchTo: Locale,
): ContextWrapper {
    var context = ctx
    val resources: Resources = context.resources
    val configuration: Configuration = resources.configuration
    val localeList = LocaleList(localeToSwitchTo)
    LocaleList.setDefault(localeList)
    configuration.setLocales(localeList)
    context = context.createConfigurationContext(configuration)
    return ContextWrapper(context)
}

fun Activity.hideStatusBar() {
    window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LOW_PROFILE
            )
}