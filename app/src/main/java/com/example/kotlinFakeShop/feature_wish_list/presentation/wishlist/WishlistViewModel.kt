package com.example.kotlinFakeShop.feature_wish_list.presentation.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinFakeShop.core.util.UiEvents
import com.example.kotlinFakeShop.feature_wish_list.domain.model.Wishlist
import com.example.kotlinFakeShop.feature_wish_list.domain.repository.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val repository: WishlistRepository
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    val wishlistItems = repository.getWishlist()/*.value?.map { it.toDomain() }*/

    fun insertFavorite(wishlist: Wishlist) {
        viewModelScope.launch {
            repository.insertToWishlist(wishlist)
        }
    }

    fun inWishlist(id: Int): LiveData<Boolean> {
        return repository.inWishlist(id)
    }

    fun deleteFromWishlist(wishlist: Wishlist) {
        viewModelScope.launch {
            repository.deleteOneWishlist(wishlist)
            _eventFlow.emit(
                UiEvents.SnackbarEvent(message = "Продукт удален из избранных")
            )
        }
    }

    fun deleteAllWishlist() {
        viewModelScope.launch {
            if (wishlistItems.value.isNullOrEmpty()) {
                _eventFlow.emit(
                    UiEvents.SnackbarEvent(message = "В избранных нет ни одного продукта")
                )
            } else {
                repository.deleteAllWishlist()
                _eventFlow.emit(
                    UiEvents.SnackbarEvent(message = "Избранные очищены")
                )
            }
        }
    }
}