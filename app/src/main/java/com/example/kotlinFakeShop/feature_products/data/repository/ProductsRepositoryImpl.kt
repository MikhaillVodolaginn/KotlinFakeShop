package com.example.kotlinFakeShop.feature_products.data.repository

import com.example.kotlinFakeShop.core.util.Resource
import com.example.kotlinFakeShop.feature_products.data.remote.ProductsApiService
import com.example.kotlinFakeShop.feature_products.data.remote.mappers.toDomain
import com.example.kotlinFakeShop.feature_products.domain.model.Product
import com.example.kotlinFakeShop.feature_products.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ProductsRepositoryImpl(private val productsApiService: ProductsApiService) :
    ProductsRepository {
    override suspend fun getProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val response = productsApiService.getProducts()
            emit(Resource.Success(response.map { it.toDomain() }))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Не удается установить соединение с сервером, пожалуйста проверьте подключение к интернету!"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Что-то пошло не так... Попробуйте еще раз!"))
        }
    }

    override suspend fun getProductCategories(): List<String> {
        return productsApiService.getProductCategories()
    }
}