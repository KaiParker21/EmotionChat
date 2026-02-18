package com.skye.emotionchat.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skye.emotionchat.domain.model.Message

@Composable
fun EmotionSummary(messages: List<Message>) {

    val summary = messages
        .mapNotNull { it.emotion?.label }
        .groupingBy { it }
        .eachCount()

    if (summary.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Text(
            text = "Emotion Summary",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        summary.forEach { (emotion, count) ->
            Text("$emotion: $count")
        }
    }
}