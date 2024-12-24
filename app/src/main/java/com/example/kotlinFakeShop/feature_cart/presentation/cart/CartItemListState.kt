package com.example.kotlinFakeShop.feature_cart.presentation.cart

import com.example.kotlinFakeShop.feature_cart.domain.model.CartProduct

data class CartItemListState(
    val cartItemList: List<CartProduct> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)