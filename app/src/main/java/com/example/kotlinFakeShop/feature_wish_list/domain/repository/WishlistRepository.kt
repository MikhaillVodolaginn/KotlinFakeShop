package com.example.kotlinFakeShop.feature_wish_list.domain.repository

import androidx.lifecycle.LiveData
import com.example.kotlinFakeShop.feature_wish_list.data.local.WishlistEntity
import com.example.kotlinFakeShop.feature_wish_list.domain.model.Wishlist

interface WishlistRepository {
    suspend fun insertToWishlist(wishlist: Wishlist)
    fun getWishlist(): LiveData<List<WishlistEntity>>
    fun inWishlist(id: Int): LiveData<Boolean>
    fun getOneWishlistItem(id: Int): LiveData<WishlistEntity?>
    suspend fun deleteOneWishlist(wishlist: Wishlist)
    suspend fun deleteAllWishlist()
}