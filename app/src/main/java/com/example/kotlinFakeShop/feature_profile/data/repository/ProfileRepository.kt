package com.example.kotlinFakeShop.feature_profile.data.repository

import com.example.kotlinFakeShop.feature_auth.data.local.AuthPreferences
import kotlinx.coroutines.flow.Flow

class ProfileRepository(private val authPreferences: AuthPreferences) {
    fun getUserProfile(): Flow<String> {
        return authPreferences.getUser
    }
}