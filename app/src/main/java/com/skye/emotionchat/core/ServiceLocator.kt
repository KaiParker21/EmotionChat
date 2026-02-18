package com.skye.emotionchat.core

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.skye.emotionchat.data.AuthRepositoryImpl
import com.skye.emotionchat.data.ChatRepositoryImpl
import com.skye.emotionchat.data.FirebaseAuthDataSource
import com.skye.emotionchat.data.FirestoreDataSource
import com.skye.emotionchat.data.UserRepositoryImpl

object ServiceLocator {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val authDataSource = FirebaseAuthDataSource(auth)
    private val firestoreDataSource = FirestoreDataSource(firestore)

    val authRepository = AuthRepositoryImpl(authDataSource, firestoreDataSource, firestore)
    val chatRepository = ChatRepositoryImpl(firestoreDataSource)
    val userRepository = UserRepositoryImpl(firestore)

}