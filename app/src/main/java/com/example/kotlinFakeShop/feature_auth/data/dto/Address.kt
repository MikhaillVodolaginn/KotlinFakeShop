package com.example.kotlinFakeShop.feature_auth.data.dto


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("city")
    val city: String,
    @SerializedName("number")
    val number: Int,
    @SerializedName("street")
    val street: String
)