package com.skye.emotionchat.data

import com.skye.emotionchat.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authDataSource: FirebaseAuthDataSource,
    private val firestoreDataSource: FirestoreDataSource
) : AuthRepository {

    override suspend fun login(email: String, password: String) {
        authDataSource.login(email, password)
    }

    override suspend fun register(email: String, password: String) {
        authDataSource.register(email, password)
    }

    override fun getCurrentUserId(): String? {
        return authDataSource.currentUserId()
    }
}