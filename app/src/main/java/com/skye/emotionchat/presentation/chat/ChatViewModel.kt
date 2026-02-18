package com.skye.emotionchat.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skye.emotionchat.core.ServiceLocator
import com.skye.emotionchat.domain.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val repository = ServiceLocator.chatRepository
    private val authRepository = ServiceLocator.authRepository

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    fun observe(chatId: String) {
        viewModelScope.launch {
            repository.observeMessages(chatId).collect {
                _messages.value = it
            }
        }
    }

    fun send(chatId: String, text: String, receiverId: String) {
        val senderId = authRepository.getCurrentUserId() ?: return

        viewModelScope.launch {
            repository.sendMessage(
                chatId,
                Message(
                    senderId = senderId,
                    receiverId = receiverId,
                    text = text
                )
            )
        }
    }
}