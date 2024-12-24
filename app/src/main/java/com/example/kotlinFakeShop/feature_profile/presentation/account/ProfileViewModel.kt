package com.example.kotlinFakeShop.feature_profile.presentation.account

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.example.kotlinFakeShop.core.util.Resource
import com.example.kotlinFakeShop.core.util.UiEvents
import com.example.kotlinFakeShop.destinations.AuthDashboardScreenDestination
import com.example.kotlinFakeShop.feature_auth.data.dto.UserResponseDto
import com.example.kotlinFakeShop.feature_auth.domain.use_case.LogoutUseCase
import com.example.kotlinFakeShop.feature_profile.data.repository.ProfileRepository
import com.example.kotlinFakeShop.feature_profile.data.toDomain
import com.example.kotlinFakeShop.feature_profile.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val logoutUseCase: LogoutUseCase,
    private val gson: Gson,
) : ViewModel() {

    private val _profileState = mutableStateOf(User())
    val profileState: State<User> = _profileState

    fun getProfile() {
        viewModelScope.launch {
            profileRepository.getUserProfile().collectLatest { data ->
                val user = gson.fromJson(data, UserResponseDto::class.java)
                _profileState.value = user.toDomain()
            }
        }
    }

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun logout() {
        viewModelScope.launch {
            when (val result = logoutUseCase()) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvents.NavigateEvent(route = AuthDashboardScreenDestination.route)
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = result.message ?: "Что-то пошло не так..."
                        )
                    )
                }
                else -> {}
            }
        }
    }
}