package com.skye.emotionchat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.skye.emotionchat.presentation.auth.LoginScreen
import com.skye.emotionchat.presentation.auth.RegisterScreen
import com.skye.emotionchat.presentation.chat.ChatScreen
import com.skye.emotionchat.presentation.home.HomeScreen
import com.skye.emotionchat.presentation.splash.SplashScreen

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(
            Screen.Chat.route,
            arguments = listOf(
                navArgument("chatId") { type = NavType.StringType },
                navArgument("receiverId") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            val receiverId = backStackEntry.arguments?.getString("receiverId") ?: ""

            ChatScreen(chatId, receiverId, navController)
        }
    }
}