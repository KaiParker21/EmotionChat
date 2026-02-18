package com.skye.emotionchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.skye.emotionchat.presentation.navigation.AppNavGraph
import com.skye.emotionchat.ui.theme.EmotionChatTheme

class MainActivity :
    ComponentActivity() {
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmotionChatTheme {
                AppNavGraph()
            }
        }
    }
}