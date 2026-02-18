package com.skye.emotionchat.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChatScreen(
    chatId: String,
    receiverId: String,
    viewModel: ChatViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val messages by viewModel.messages.collectAsState()
    var text by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.observe(chatId)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        val summary = messages
            .mapNotNull { it.emotion?.label }
            .groupingBy { it }
            .eachCount()

        summary.forEach { (emotion, count) ->
            Text("$emotion: $count")
        }

        EmotionSummary(messages)

        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = false
        ) {
            items(messages) { message ->
                MessageItem(message = message)
            }
        }


        Row(modifier = Modifier.padding(8.dp)) {

            MessageInput(
                text = text,
                onTextChange = { text = it },
                onSend = {
                    viewModel.send(chatId, text, receiverId)
                    text = ""
                }
            )

        }
    }
}