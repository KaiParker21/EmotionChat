package com.skye.emotionchat.data

import com.google.firebase.firestore.FirebaseFirestore
import com.skye.emotionchat.domain.model.User
import com.skye.emotionchat.domain.repository.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserRepositoryImpl(
    private val firestore: FirebaseFirestore
) : UserRepository {

    override fun observeUsers(): Flow<List<User>> = callbackFlow {

        val listener = firestore.collection("users")
            .addSnapshotListener { snapshot, _ ->

                val users = snapshot?.documents?.map {
                    User(
                        uid = it.getString("uid") ?: "",
                        email = it.getString("email") ?: ""
                    )
                } ?: emptyList()

                trySend(users)
            }

        awaitClose { listener.remove() }
    }
}
