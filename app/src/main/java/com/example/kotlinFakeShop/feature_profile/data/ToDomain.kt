package com.example.kotlinFakeShop.feature_profile.data

import com.example.kotlinFakeShop.feature_auth.data.dto.UserResponseDto
import com.example.kotlinFakeShop.feature_profile.domain.model.User

internal fun UserResponseDto.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email,
        username = username,
        password = password,
        phone = phone,
        address = address
    )
}