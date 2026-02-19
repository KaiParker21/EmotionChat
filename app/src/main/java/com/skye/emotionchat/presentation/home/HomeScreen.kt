package com.skye.emotionchat.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.skye.emotionchat.core.ServiceLocator
import com.skye.emotionchat.presentation.navigation.Screen

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {

    val users by viewModel.users.collectAsState()
    val currentUser = ServiceLocator.authRepository.getCurrentUserId()

    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "Users",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn {

            items(users) { user ->

                Text(
                    text = user.username,
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
