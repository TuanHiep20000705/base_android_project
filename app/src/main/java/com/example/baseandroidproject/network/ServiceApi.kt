package com.example.baseandroidproject.network

import com.example.baseandroidproject.constants.LOGIN
import com.example.baseandroidproject.data.request.LoginRequest
import com.example.baseandroidproject.data.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ServiceApi {
    @POST(LOGIN)
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): LoginResponse
}