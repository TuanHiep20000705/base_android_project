package com.example.baseandroidproject.data.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
)
