package com.skye.emotionchat.presentation.chat.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import com.skye.emotionchat.domain.model.Message

@Composable
fun AnimatedMessageItem(
    message: Message,
    previousMessage: Message?
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInVertically { it / 3 }
    ) {
        ModernMessageBubble(
            message = message,
            isGrouped = previousMessage?.senderId == message.senderId
        )
    }
}
