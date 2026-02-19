package com.skye.emotionchat.domain.repository

interface AuthRepository {
    suspend fun login(email: String, password: String)
    suspend fun register(email: String, username: String, password: String)
    fun getCurrentUserId(): String?
    fun logout()
}