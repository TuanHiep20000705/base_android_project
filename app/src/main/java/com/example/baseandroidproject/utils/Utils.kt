package com.example.baseandroidproject.utils

import androidx.annotation.StringRes
import com.example.baseandroidproject.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object Utils {
    fun getString(
        @StringRes stringRes: Int,
    ): String {
        return App.instance.getString(stringRes)
    }

    fun <T> debounce(
        delayMillis: Long = 800L,
        scope: CoroutineScope,
        action: (T) -> Unit,
    ): (T) -> Unit {
        var debounceJob: Job? = null
        return { param: T ->
            if (debounceJob == null) {
                debounceJob =
                    scope.launch {
                        action(param)
                        delay(delayMillis)
                        debounceJob = null
                    }
            }
        }
    }
}