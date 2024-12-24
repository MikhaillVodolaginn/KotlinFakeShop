package com.example.kotlinFakeShop.feature_auth.data.dto


import com.google.gson.annotations.SerializedName

data class UserResponseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: Name,
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("address")
    val address: Address
)