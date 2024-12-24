package com.example.kotlinFakeShop.feature_products.domain.repository

import com.example.kotlinFakeShop.core.util.Resource
import com.example.kotlinFakeShop.feature_products.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProducts(): Flow<Resource<List<Product>>>
    suspend fun getProductCategories(): List<String>
}