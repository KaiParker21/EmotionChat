package com.skye.emotionchat.core

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
object ServiceLocator {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val authDataSource = FirebaseAuthDataSource(auth)
    private val firestoreDataSource = FirestoreDataSource(firestore)

    val authRepository = AuthRepositoryImpl(authDataSource, firestoreDataSource)
    val chatRepository = ChatRepositoryImpl(firestoreDataSource)
}