package com.msah.mobilepos.auth.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.msah.mobilepos.data.repository.auth.AuthRepository
import com.msah.mobilepos.data.repository.user.UserRepository

class SignUpViewModelFactory(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpViewModel(authRepository, userRepository) as T
    }
}