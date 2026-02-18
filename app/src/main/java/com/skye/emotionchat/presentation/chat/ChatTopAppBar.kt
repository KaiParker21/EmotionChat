package com.skye.emotionchat.presentation.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.skye.emotionchat.domain.model.Message

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    receiverId: String,
    messages: List<Message>,
    onBack: () -> Unit
) {

    val dominantEmotion = messages
        .mapNotNull { it.emotion?.label }
        .groupingBy { it }
        .eachCount()
        .maxByOrNull { it.value }
        ?.key

    CenterAlignedTopAppBar(
        title = {
            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                Text(text = receiverId)

                if (dominantEmotion != null) {
                    Text(
                        text = "Mostly $dominantEmotion",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}
