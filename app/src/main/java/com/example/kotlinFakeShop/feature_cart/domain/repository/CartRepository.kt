package com.example.kotlinFakeShop.feature_cart.domain.repository

import com.example.kotlinFakeShop.core.util.Resource
import com.example.kotlinFakeShop.feature_cart.domain.model.CartProduct
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getAllCartItems(id: Int): Flow<Resource<List<CartProduct>>>
}