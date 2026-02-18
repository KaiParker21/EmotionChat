package com.skye.emotionchat.data

import com.google.firebase.firestore.FirebaseFirestore
import com.skye.emotionchat.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authDataSource: FirebaseAuthDataSource,
    private val firestoreDataSource: FirestoreDataSource,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun login(email: String, password: String) {
        authDataSource.login(email, password)
    }

    override suspend fun register(email: String, password: String) {
        authDataSource.register(email, password)

        val uid = authDataSource.currentUserId() ?: return

        firestore.collection("users")
            .document(uid)
            .set(
                mapOf(
                    "uid" to uid,
                    "email" to email,
                    "createdAt" to System.currentTimeMillis()
                )
            )
    }


    override fun getCurrentUserId(): String? {
        return authDataSource.currentUserId()
    }
}