package com.example.kotlinFakeShop.feature_auth.domain.use_case

import com.example.kotlinFakeShop.core.util.Resource
import com.example.kotlinFakeShop.feature_auth.domain.repository.LoginRepository

class AutoLoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): Resource<Unit> {
        return loginRepository.loginAuto()
    }
}