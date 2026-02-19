package com.skye.emotionchat.presentation.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.skye.emotionchat.presentation.auth.LoginScreen
import com.skye.emotionchat.presentation.auth.RegisterScreen
import com.skye.emotionchat.presentation.chat.ChatScreen
import com.skye.emotionchat.presentation.home.HomeScreen
import com.skye.emotionchat.presentation.splash.SplashScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val animationDuration = 400

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(animationDuration))
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(animationDuration))
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(animationDuration))
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(animationDuration))
        }
    ) {

        composable(
            route = Screen.Splash.route,
            exitTransition = { fadeOut(tween(animationDuration)) }
        ) {
            SplashScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }

        composable(
            route = Screen.Home.route,
            enterTransition = { fadeIn(tween(animationDuration)) },
            exitTransition = { fadeOut(tween(animationDuration)) }
        ) {
            val activity = androidx.activity.compose.LocalActivity.current
            BackHandler {
                activity?.finish()
            }

            HomeScreen(navController)
        }

        composable(
            route = Screen.Chat.route,
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