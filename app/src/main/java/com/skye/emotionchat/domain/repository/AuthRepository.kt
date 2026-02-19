package com.skye.emotionchat.domain.repository

import com.skye.emotionchat.core.Resource

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<Unit>
    suspend fun register(email: String, username: String, password: String): Resource<Unit>
    fun getCurrentUserId(): String?
    fun logout()
}