package com.skye.emotionchat.domain.repository

import com.skye.emotionchat.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeUsers(): Flow<List<User>>
    suspend fun getUserById(uid: String): User?
}
