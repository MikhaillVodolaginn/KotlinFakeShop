package com.example.kotlinFakeShop.feature_cart.di

import com.google.gson.Gson
import com.example.kotlinFakeShop.core.util.Constants
import com.example.kotlinFakeShop.feature_auth.data.local.AuthPreferences
import com.example.kotlinFakeShop.feature_cart.data.remote.CartApiService
import com.example.kotlinFakeShop.feature_cart.data.repository.CartRepositoryImpl
import com.example.kotlinFakeShop.feature_cart.domain.repository.CartRepository
import com.example.kotlinFakeShop.feature_cart.domain.use_case.GetCartItemListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {

    @Provides
    @Singleton
    fun provideCartApiService(): CartApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CartApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        cartApiService: CartApiService
    ): CartRepository {
        return CartRepositoryImpl(
            cartApiService
        )
    }

    @Provides
    @Singleton
    fun provideGetCartItemsUseCase(
        cartRepository: CartRepository,
        authPreferences: AuthPreferences,
        gson: Gson
    ): GetCartItemListUseCase {
        return GetCartItemListUseCase(cartRepository, authPreferences, gson)
    }
}