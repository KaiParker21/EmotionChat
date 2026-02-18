package com.skye.emotionchat.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skye.emotionchat.domain.model.Message

@Composable
fun EmotionSummaryRow(messages: List<Message>) {

    val summary = messages
        .mapNotNull { it.emotion?.label }
        .groupingBy { it }
        .eachCount()

    if (summary.isEmpty()) return

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        summary.forEach { (emotion, count) ->
            AssistChip(
                onClick = {},
                label = { Text("$emotion ($count)") }
            )
        }
    }
}
