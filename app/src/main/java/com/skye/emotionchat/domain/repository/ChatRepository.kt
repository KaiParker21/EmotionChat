package com.skye.emotionchat.domain.repository

import com.skye.emotionchat.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun observeMessages(chatId: String): Flow<List<Message>>
    suspend fun sendMessage(chatId: String, message: Message)
}