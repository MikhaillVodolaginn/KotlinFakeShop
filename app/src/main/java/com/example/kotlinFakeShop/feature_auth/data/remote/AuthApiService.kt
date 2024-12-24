package com.example.kotlinFakeShop.feature_auth.data.remote

import com.example.kotlinFakeShop.feature_auth.data.dto.UserResponseDto
import com.example.kotlinFakeShop.feature_auth.data.remote.request.LoginRequest
import com.example.kotlinFakeShop.feature_auth.data.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("users/")
    suspend fun getUserList(): List<UserResponseDto>
}