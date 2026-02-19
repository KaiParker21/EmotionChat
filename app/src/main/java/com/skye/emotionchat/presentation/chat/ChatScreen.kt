package com.skye.emotionchat.presentation.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.skye.emotionchat.core.ServiceLocator

@Composable
fun ChatScreen(
    chatId: String,
    receiverId: String,
    navController: NavController,
    viewModel: ChatViewModel = viewModel()
) {
    val messages by viewModel.messages.collectAsState()
    var text by remember { mutableStateOf("") }
    val receiverUsername by viewModel.receiverUsername.collectAsState()

    val currentUser = ServiceLocator.authRepository.getCurrentUserId()

    val displayName = if (receiverId == currentUser) {
        "Saved Messages"
    } else {
        receiverUsername
    }


    val listState = rememberLazyListState()

    LaunchedEffect(chatId) {
        viewModel.observe(chatId)
    }

    LaunchedEffect(receiverId) {
        if (receiverId != currentUser) {
            viewModel.loadReceiver(receiverId)
        }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            ChatTopBar(
                username = displayName,
                messages = messages,
                onBack = { navController.popBackStack() }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            EmotionSummaryRow(messages)

            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                itemsIndexed(messages) { index, message ->
                    AnimatedMessageItem(
                        message = message,
                        previousMessage = messages.getOrNull(index - 1)
                    )
                }
            }

            ModernMessageInput(
                text = text,
                onTextChange = { text = it },
                onSend = {
                    if (text.isNotBlank()) {
                        viewModel.send(chatId, text, receiverId)
                        text = ""
                    }
                }
            )
        }
    }
}
