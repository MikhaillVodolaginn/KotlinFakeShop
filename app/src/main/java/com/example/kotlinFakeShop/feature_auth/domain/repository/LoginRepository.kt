package com.example.kotlinFakeShop.feature_auth.domain.repository

import com.example.kotlinFakeShop.core.util.Resource
import com.example.kotlinFakeShop.feature_auth.data.remote.request.LoginRequest

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest, rememberMe: Boolean): Resource<Unit>
    suspend fun loginAuto(): Resource<Unit>
    suspend fun logout(): Resource<Unit>
}
