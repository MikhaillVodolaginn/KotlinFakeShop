package com.example.kotlinFakeShop.feature_products.domain.use_case

import com.example.kotlinFakeShop.core.util.Resource
import com.example.kotlinFakeShop.feature_products.domain.model.Product
import com.example.kotlinFakeShop.feature_products.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Product>>> {
        return productsRepository.getProducts()
    }
}