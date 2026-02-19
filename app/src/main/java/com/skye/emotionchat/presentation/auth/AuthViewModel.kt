package com.skye.emotionchat.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skye.emotionchat.core.Resource
import com.skye.emotionchat.core.ServiceLocator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

data class RegisterUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class AuthViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState
    private val _registerState = MutableStateFlow(RegisterUiState())
    val registerState: StateFlow<RegisterUiState> = _registerState


    private val repo = ServiceLocator.authRepository

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            when (val result = repo.login(email, password)) {
                is Resource.Success -> {
                    _uiState.value = AuthUiState(isSuccess = true)
                }
                is Resource.Error -> {
                    _uiState.value = AuthUiState(
                        error = result.message
                    )
                }
                else -> {}
            }
        }
    }


    fun register(
        email: String,
        username: String,
        password: String
    ) {
        viewModelScope.launch {
            _registerState.value = RegisterUiState(isLoading = true)
            when (val result = repo.register(email, username, password)) {
                is Resource.Success -> {
                    _registerState.value = RegisterUiState(isSuccess = true)
                }
                is Resource.Error -> {
                    _registerState.value = RegisterUiState(
                        error = result.message
                    )
                }
                else -> {}
            }
        }
    }
}