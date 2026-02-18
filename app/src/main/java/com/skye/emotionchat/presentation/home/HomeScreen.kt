package com.skye.emotionchat.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skye.emotionchat.core.ServiceLocator
import com.skye.emotionchat.domain.model.User
import com.skye.emotionchat.presentation.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {

    val firestore = ServiceLocator.chatRepository
    val currentUser = ServiceLocator.authRepository.getCurrentUserId()

    var users by remember { mutableStateOf(listOf<User>()) }

    LaunchedEffect(Unit) {
        ServiceLocator.chatRepository
    }

    Column {

        Text("Users", style = MaterialTheme.typography.titleLarge)

        LazyColumn {
            items(users) { user ->

                if (user.uid != currentUser) {

                    Text(
                        text = user.email,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {

                                val chatId =
                                    listOf(currentUser!!, user.uid)
                                        .sorted()
                                        .joinToString("_")

                                navController.navigate(
                                    Screen.Chat.create(chatId, user.uid)
                                )
                            }
                    )
                }
            }
        }
    }
}