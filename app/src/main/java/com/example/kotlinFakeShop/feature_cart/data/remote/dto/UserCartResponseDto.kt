package com.example.kotlinFakeShop.feature_cart.data.remote.dto


import com.google.gson.annotations.SerializedName

data class UserCartResponseDto(
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("products")
    val cartProductDtos: List<CartProductDto>,
    @SerializedName("userId")
    val userId: Int
)