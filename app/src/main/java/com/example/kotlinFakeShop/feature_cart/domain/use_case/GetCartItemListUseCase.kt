package com.example.kotlinFakeShop.feature_cart.domain.use_case

import com.example.kotlinFakeShop.core.util.Resource
import com.example.kotlinFakeShop.feature_auth.data.dto.UserResponseDto
import com.example.kotlinFakeShop.feature_auth.data.local.AuthPreferences
import com.example.kotlinFakeShop.feature_cart.domain.model.CartProduct
import com.example.kotlinFakeShop.feature_cart.domain.repository.CartRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GetCartItemListUseCase(
    private val cartRepository: CartRepository,
    private val authPreferences: AuthPreferences,
    private val gson: Gson

) {
    suspend operator fun invoke(): Flow<Resource<List<CartProduct>>> {
        val data = authPreferences.getUser.first()
        val user = gson.fromJson(data, UserResponseDto::class.java)
        return cartRepository.getAllCartItems(user.id)
    }
}