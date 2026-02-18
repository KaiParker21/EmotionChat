package com.skye.emotionchat.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skye.emotionchat.domain.model.Message
import com.skye.emotionchat.core.ServiceLocator

@Composable
fun MessageItem(message: Message) {

    val currentUser = ServiceLocator.authRepository.getCurrentUserId()
    val isMine = message.senderId == currentUser

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = if (isMine)
            androidx.compose.ui.Alignment.End
        else
            androidx.compose.ui.Alignment.Start
    ) {

        Box(
            modifier = Modifier
                .background(
                    color = if (isMine)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {

            Column {

                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(6.dp))

                EmotionBadge(message)
            }
        }
    }
}