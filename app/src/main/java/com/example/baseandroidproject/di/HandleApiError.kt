package com.example.baseandroidproject.di

import android.content.Context
import com.example.baseandroidproject.R
import com.example.baseandroidproject.data.model.ErrorMessageException
import com.example.baseandroidproject.data.response.ErrorResponse
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import timber.log.Timber

class HandleApiError @Inject
constructor(
    @ApplicationContext private val context: Context,
) {
    fun getMessage(throwable: Throwable): String =
        when (throwable) {
            is ErrorMessageException -> {
                Timber.e(throwable)
                throwable.errorMessage
            }

            is HttpException -> {
                Timber.e(throwable)
                tryExtractErrorMessage(throwable) ?: context.getString(R.string.error_unknown)
            }

            else -> {
                if (throwable !is SocketTimeoutException && throwable !is UnknownHostException) {
                    Timber.e(throwable, "Unknown exception")
                }
                context.getString(R.string.error_disconnected)
            }
        }

    private fun tryExtractErrorMessage(exception: HttpException): String? {
        return runCatching {
            val response = exception.asResponse()
            return@runCatching response?.message
        }.onFailure {
            Timber.e(it, "Failed to parse JSON from server side")
        }.getOrNull()
    }

    private fun HttpException.asResponse(): ErrorResponse? {
        val body = response()?.errorBody()?.string() ?: return null
        return Gson().fromJson(body, ErrorResponse::class.java)
    }
}