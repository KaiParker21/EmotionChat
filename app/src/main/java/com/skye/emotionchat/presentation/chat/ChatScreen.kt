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

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(messages) { message ->
                Text(
                    text = "${message.text} (${message.emotion?.label ?: "Analyzing..."})",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Row(modifier = Modifier.padding(8.dp)) {

            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    viewModel.send(chatId, text, receiverId)
                    text = ""
                }
            ) {
                Text("Send")
            }
        }
    }
}