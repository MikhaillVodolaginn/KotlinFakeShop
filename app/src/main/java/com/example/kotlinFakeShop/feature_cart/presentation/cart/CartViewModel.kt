package com.example.kotlinFakeShop.feature_cart.presentation.cart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinFakeShop.core.util.Resource
import com.example.kotlinFakeShop.core.util.UiEvents
import com.example.kotlinFakeShop.feature_cart.domain.use_case.GetCartItemListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemListUseCase
) : ViewModel() {
    private val _state = mutableStateOf(CartItemListState())
    val state: State<CartItemListState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            getCartItems()
        }
    }

    private suspend fun getCartItems() {
        getCartItemsUseCase().collectLatest { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        cartItemList = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = result.message ?: "Что-то пошло не так..."
                        )
                    )
                }
            }
        }
    }
}