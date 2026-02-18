package com.skye.emotionchat.presentation.chat

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skye.emotionchat.domain.model.Message

@Composable
fun EmotionBadge(message: Message) {

    val emotion = message.emotion

    val label = emotion?.label ?: "Analyzing..."
    val confidence = emotion?.confidence?.let { String.format("%.2f", it) }

    val color = when (emotion?.label) {
        "joy" -> Color(0xFF4CAF50)
        "sadness" -> Color(0xFF2196F3)
        "anger" -> Color(0xFFF44336)
        "fear" -> Color(0xFF9C27B0)
        else -> Color.Gray
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.15f)
    ) {
        Text(
            text = if (confidence != null)
                "$label ($confidence)"
            else
                label,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}