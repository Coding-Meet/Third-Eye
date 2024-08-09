package com.coding.meet.thirdeye

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.coding.meet.thirdeye.ui.navigation.NavGraph
import com.coding.meet.thirdeye.ui.theme.BlindAIAssistantAppTheme
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {
    private val textToSpeech: TextToSpeech by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            BlindAIAssistantAppTheme {
                NavGraph(this)
            }
        }
    }
    override fun onPause() {
        textToSpeech.stop()
        super.onPause()
    }

    override fun onDestroy() {
        textToSpeech.stop()
        super.onDestroy()
    }
}