package com.example.kotlinFakeShop.feature_cart.data.repository

import com.example.kotlinFakeShop.core.util.Resource
import com.example.kotlinFakeShop.feature_cart.data.remote.CartApiService
import com.example.kotlinFakeShop.feature_cart.domain.model.CartProduct
import com.example.kotlinFakeShop.feature_cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CartRepositoryImpl(
    private val cartApiService: CartApiService
) : CartRepository {
    override suspend fun getAllCartItems(id: Int): Flow<Resource<List<CartProduct>>> = flow {
        emit(Resource.Loading())
        try {
            val response = cartApiService.cartItems(id)
            val cartItemList = ArrayList<CartProduct>()
            response.forEach { group ->
                group.cartProductDtos.forEach {
                    val productResponse = cartApiService.product(it.productId)
                    val cartProduct = CartProduct(
                        productResponse.title,
                        productResponse.price,
                        it.quantity,
                        productResponse.image
                    )
                    cartItemList.add(cartProduct)
                }
            }
            emit(Resource.Success(cartItemList.toList().distinctBy { it.name }))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Не удается установить соединение с сервером, пожалуйста проверьте подключение к интернету!"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Что-то пошло не так... Попробуйте еще раз!"))
        }
    }
}