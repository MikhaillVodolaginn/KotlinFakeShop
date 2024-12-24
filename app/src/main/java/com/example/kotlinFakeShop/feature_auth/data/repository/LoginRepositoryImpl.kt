package com.example.kotlinFakeShop.feature_auth.data.repository

import com.example.kotlinFakeShop.core.util.Resource
import com.example.kotlinFakeShop.feature_auth.data.dto.UserResponseDto
import com.example.kotlinFakeShop.feature_auth.data.local.AuthPreferences
import com.example.kotlinFakeShop.feature_auth.data.remote.AuthApiService
import com.example.kotlinFakeShop.feature_auth.data.remote.request.LoginRequest
import com.example.kotlinFakeShop.feature_auth.domain.repository.LoginRepository
import java.io.IOException
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class LoginRepositoryImpl(
    private val authApiService: AuthApiService,
    private val authPreferences: AuthPreferences
) : LoginRepository {
    override suspend fun login(loginRequest: LoginRequest, rememberMe: Boolean): Resource<Unit> {
        return try {
            val response = authApiService.login(loginRequest)
            getUserList(loginRequest.username)?.let { authPreferences.saveUser(it) }

            if (rememberMe) {
                authPreferences.saveAccessToken(response.token)
            }

            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error(message = "Не удается установить соединение с сервером, пожалуйста проверьте подключение к интернету!")
        } catch (e: HttpException) {
            Resource.Error(message = "Что-то пошло не так... Попробуйте еще раз!")
        }
    }

    override suspend fun loginAuto(): Resource<Unit> {
        val accessToken = authPreferences.getAccessToken.first()
        return if (accessToken != "") Resource.Success(Unit) else Resource.Error("")
    }

    override suspend fun logout(): Resource<Unit> {
        return try {
            authPreferences.clearAccessToken()
            val fetchedToken = authPreferences.getAccessToken.first()

            if (fetchedToken.isEmpty()) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Что-то пошло не так...")
            }
        } catch (e: Exception) {
            return Resource.Error("Что-то пошло не так...")
        }
    }

    private suspend fun getUserList(name: String): UserResponseDto? {
        val response = authApiService.getUserList()
        return response.find { it.username == name }
    }
}