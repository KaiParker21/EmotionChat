package com.skye.emotionchat.data

import com.skye.emotionchat.domain.model.Message
import com.skye.emotionchat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class ChatRepositoryImpl(
    private val firestoreDataSource: FirestoreDataSource
) : ChatRepository {

    override fun observeMessages(chatId: String): Flow<List<Message>> {
        return firestoreDataSource.observeMessages(chatId)
    }

    override suspend fun sendMessage(chatId: String, message: Message) {
        firestoreDataSource.sendMessage(chatId, message)
    }
}