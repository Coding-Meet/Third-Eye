package com.coding.meet.blindaiassistant

import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.coding.meet.blindaiassistant.ui.theme.BlindAIAssistantAppTheme
import com.coding.meet.blindaiassistant.util.CAMERAX_PERMISSIONS
import org.koin.android.ext.android.inject
import com.coding.meet.blindaiassistant.ui.navigation.NavGraph
import com.coding.meet.blindaiassistant.util.showToast
import kotlinx.coroutines.delay
import org.koin.compose.KoinContext
import org.koin.core.context.KoinContext


class MainActivity : ComponentActivity() {
    private val textToSpeech: TextToSpeech by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlindAIAssistantAppTheme {
                LaunchedEffect(Unit) {
                    delay(1000)
                    if (!hasRequiredPermissions()) {
                        showToast(R.string.camera_permission_request)
                        ActivityCompat.requestPermissions(
                            this@MainActivity, CAMERAX_PERMISSIONS, 0
                        )
                    } else {
                       showToast(R.string.swipe_anywhere_on_the_bottom_of_the_screen_to_activate)
                    }
                }
                NavGraph()
            }
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
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