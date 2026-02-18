package com.skye.emotionchat.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skye.emotionchat.core.ServiceLocator
import com.skye.emotionchat.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val userRepository = ServiceLocator.userRepository
    private val authRepository = ServiceLocator.authRepository

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    init {
        viewModelScope.launch {
            userRepository.observeUsers().collect { allUsers ->
                val currentUid = authRepository.getCurrentUserId()
                _users.value = allUsers.filter { it.uid != currentUid }
            }
        }
    }
}
