package com.skye.emotionchat.data

import com.google.firebase.firestore.FirebaseFirestore
import com.skye.emotionchat.core.Resource
import com.skye.emotionchat.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val authDataSource: FirebaseAuthDataSource,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun login(email: String, password: String): Resource<Unit> {
        return try {
            authDataSource.login(email, password)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(
                message = e.message ?: "Login failed",
                throwable = e
            )
        }
    }

    override suspend fun register(email: String, username: String, password: String): Resource<Unit> {
        return try {
            authDataSource.register(email, password)

            val uid = authDataSource.currentUserId()
                ?: return Resource.Error("User ID not found")

            firestore.collection("users")
                .document(uid)
                .set(
                    mapOf(
                        "uid" to uid,
                        "email" to email,
                        "username" to username,
                        "createdAt" to System.currentTimeMillis()
                    )
                )
                .await()

            Resource.Success(Unit)

        } catch (e: Exception) {

            Resource.Error(
                message = mapFirebaseError(e),
                throwable = e
            )
        }
    }


    override fun getCurrentUserId(): String? {
        return authDataSource.currentUserId()
    }

    override fun logout() {
        authDataSource.logout()
    }

}

private fun mapFirebaseError(e: Exception): String {

    val message = e.message ?: return "Registration failed"

    return when {
        message.contains("email address is already in use", true) ->
            "Email already registered"

        message.contains("badly formatted", true) ->
            "Invalid email format"

        message.contains("Password should be at least", true) ->
            "Password must be at least 6 characters"

        else -> "Registration failed. Please try again."
    }
}
