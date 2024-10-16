package com.example.baseandroidproject.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager

object KeyboardUtils {
    @JvmStatic
    fun hideSoftKeyboard(activity: Activity?) {
        if (activity == null) {
            return
        }

        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }
}