package com.ilyaemeliyanov.mx_frontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ilyaemeliyanov.mx_frontend.data.user.UserRepository

class MXViewModelFactory(private val userRepository: UserRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MXViewModel::class.java)) {
            return MXViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
