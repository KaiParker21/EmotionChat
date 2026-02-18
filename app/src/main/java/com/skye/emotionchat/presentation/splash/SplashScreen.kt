package com.skye.emotionchat.presentation.splash

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.navigation.NavController
import com.skye.emotionchat.core.ServiceLocator
import com.skye.emotionchat.presentation.navigation.Screen

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        val user = ServiceLocator.authRepository.getCurrentUserId()
        if (user != null) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}