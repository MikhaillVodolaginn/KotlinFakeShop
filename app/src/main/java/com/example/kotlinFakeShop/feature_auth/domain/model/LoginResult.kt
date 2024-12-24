package com.example.kotlinFakeShop.feature_auth.domain.model

import com.example.kotlinFakeShop.core.util.Resource

data class LoginResult(
    val usernameError: String? = null,
    val passwordError: String? = null,
    val result: Resource<Unit>? = null
)
