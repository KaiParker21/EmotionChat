package com.skye.emotionchat.presentation.chat

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ModernMessageInput(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit
) {

    val isEnabled = text.isNotBlank()

    val sendColor by animateColorAsState(
        if (isEnabled)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
    )

    Surface(
        tonalElevation = 6.dp,
        shadowElevation = 6.dp,
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {

            TextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message...") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.surface
                ),
                maxLines = 4,
                shape = RoundedCornerShape(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { if (isEnabled) onSend() },
                enabled = isEnabled
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = sendColor
                )
            }
        }
    }
}
