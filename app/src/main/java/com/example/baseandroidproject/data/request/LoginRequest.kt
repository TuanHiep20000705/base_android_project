package com.example.baseandroidproject.data.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val userName: String,
    @SerializedName("password")
    val password: String,
)
