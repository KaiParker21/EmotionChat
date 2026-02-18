package com.skye.emotionchat.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Chat : Screen("chat/{chatId}/{receiverId}") {
        fun create(chatId: String, receiverId: String) =
            "chat/$chatId/$receiverId"
    }
}