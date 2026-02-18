package com.skye.emotionchat.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.skye.emotionchat.domain.model.Emotion
import com.skye.emotionchat.domain.model.Message
import com.skye.emotionchat.domain.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreDataSource(
    private val firestore: FirebaseFirestore
) {

    fun observeMessages(chatId: String): Flow<List<Message>> = callbackFlow {

        val listener = firestore.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, _ ->

                val messages = snapshot?.documents?.map {
                    Message(
                        id = it.id,
                        senderId = it.getString("senderId") ?: "",
                        receiverId = it.getString("receiverId") ?: "",
                        text = it.getString("text") ?: "",
                        timestamp = it.getLong("timestamp") ?: 0L,
                        emotion = it.getString("emotionLabel")?.let { label ->
                            com.skye.emotionchat.domain.model.Emotion(
                                label,
                                it.getDouble("emotionConfidence") ?: 0.0
                            )
                        }
                    )
                } ?: emptyList()

                trySend(messages)
            }

        awaitClose { listener.remove() }
    }

    suspend fun sendMessage(chatId: String, message: Message) {

        val chatRef = firestore.collection("chats").document(chatId)

        // Ensure chat document exists
        chatRef.set(
            mapOf(
                "participants" to listOf(message.senderId, message.receiverId)
            ),
            SetOptions.merge()
        ).await()

        // Add message
        chatRef.collection("messages")
            .add(
                mapOf(
                    "senderId" to message.senderId,
                    "receiverId" to message.receiverId,
                    "text" to message.text,
                    "timestamp" to message.timestamp
                )
            ).await()
    }

    fun getUsers(): Flow<List<User>> = callbackFlow {
        val listener = firestore.collection("users")
            .addSnapshotListener { snapshot, _ ->
                val users = snapshot?.documents?.map {
                    User(
                        uid = it.id,
                        email = it.getString("email") ?: ""
                    )
                } ?: emptyList()
                trySend(users)
            }
        awaitClose { listener.remove() }
    }

}