package com.example.kotlinFakeShop.feature_auth.util

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val SPLASH_SCREEN_DURATION = 1000L
    const val AUTH_PREFERENCES = "AUTH_PREFERENCES"
    val AUTH_KEY = stringPreferencesKey(name = "auth_key")
    val USER_DATA = stringPreferencesKey("user_data")
}