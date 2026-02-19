package com.skye.emotionchat.presentation.chat.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skye.emotionchat.core.ServiceLocator
import com.skye.emotionchat.domain.model.Message
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ModernMessageBubble(
    message: Message,
    isGrouped: Boolean
) {

    val currentUser = ServiceLocator.authRepository.getCurrentUserId()
    val isMine = message.senderId == currentUser

    val bubbleColor = if (isMine)
        MaterialTheme.colorScheme.primaryContainer
    else
        MaterialTheme.colorScheme.surfaceVariant

    val shape = if (isMine) {
        RoundedCornerShape(
            topStart = 18.dp,
            topEnd = 4.dp,
            bottomStart = 18.dp,
            bottomEnd = 18.dp
        )
    } else {
        RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 18.dp,
            bottomStart = 18.dp,
            bottomEnd = 18.dp
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = if (isGrouped) 2.dp else 6.dp),
        horizontalAlignment = if (isMine) Alignment.End else Alignment.Start
    ) {

        Surface(
            shape = shape,
            tonalElevation = 4.dp,
            shadowElevation = 2.dp,
            color = bubbleColor,
            modifier = Modifier.widthIn(max = 320.dp)
        ) {

            Column(
                modifier = Modifier.padding(12.dp)
            ) {

                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                EmotionChip(message)

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = formatTime(message.timestamp),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun EmotionChip(message: Message) {

    val emotion = message.emotion

    val label = emotion?.label ?: "Analyzing"
    val confidence = (emotion?.confidence ?: 0.0).toFloat()


    val emotionColor = when (label.lowercase()) {
        "joy" -> Color(0xFF4CAF50)
        "sadness" -> Color(0xFF2196F3)
        "anger" -> Color(0xFFF44336)
        "fear" -> Color(0xFF9C27B0)
        "love" -> Color(0xFFE91E63)
        else -> Color.Gray
    }

    val animatedColor by animateColorAsState(emotionColor)

    Column {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = emotionIcon(label),
                contentDescription = null,
                tint = animatedColor,
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "$label ${(confidence * 100).toInt()}%",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = animatedColor
            )
        }
    }
}

private fun emotionIcon(label: String) = when (label.lowercase()) {
    "joy" -> Icons.Default.SentimentSatisfied
    "sadness" -> Icons.Default.SentimentDissatisfied
    "anger" -> Icons.Default.Whatshot
    "fear" -> Icons.Default.Mood
    "love" -> Icons.Default.SentimentSatisfied
    else -> Icons.Default.SentimentNeutral
}

private fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}


