package com.skye.emotionchat.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skye.emotionchat.core.ServiceLocator
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repo = ServiceLocator.authRepository

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repo.login(email, password)
            onSuccess()
        }
    }

    fun register(email: String, username: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repo.register(email, username, password)
            onSuccess()
        }
    }
}