package com.coding.meet.thirdeye.util

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.input.pointer.PointerInputScope
import com.coding.meet.thirdeye.R
import org.koin.mp.KoinPlatform
import java.util.Locale
import kotlin.math.abs

/**
 * Created 29-07-2024 at 10:28 am
 */

fun showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    val context: Context = KoinPlatform.getKoin().get()
    val speech: TextToSpeech = KoinPlatform.getKoin().get()
    val isLanguageSupport: Boolean = KoinPlatform.getKoin().get()
    if (isLanguageSupport) {
        speech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null)
    } else {
        Toast.makeText(context, message, duration).show()
    }
}

fun showToast(messageID: Int, duration: Int = Toast.LENGTH_LONG) {
    val speech: TextToSpeech = KoinPlatform.getKoin().get()
    val isLanguageSupport: Boolean = KoinPlatform.getKoin().get()
    val context: Context = KoinPlatform.getKoin().get()
    val message = context.getString(messageID)
    if (isLanguageSupport) {
        speech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null)
    } else {
        Toast.makeText(context, message, duration).show()
    }
}

fun showToast(messageID1: Int,messageID2: Int, duration: Int = Toast.LENGTH_LONG) {
    val speech: TextToSpeech = KoinPlatform.getKoin().get()
    val isLanguageSupport: Boolean = KoinPlatform.getKoin().get()
    val context: Context = KoinPlatform.getKoin().get()
    val message1 = context.getString(messageID1)
    val message2 = context.getString(messageID2)
    if (isLanguageSupport) {
        speech.speak(message1+message2, TextToSpeech.QUEUE_FLUSH, null, null)
    } else {
        Toast.makeText(context, message1+message2, duration).show()
    }
}
fun pauseVoice(){
    val speech: TextToSpeech = KoinPlatform.getKoin().get()
    speech.stop()
}

fun addToastSpeech(messageID: Int) {
    val speech: TextToSpeech = KoinPlatform.getKoin().get()
    val isLanguageSupport: Boolean = KoinPlatform.getKoin().get()
    val context: Context = KoinPlatform.getKoin().get()
    val message = context.getString(messageID)
    if (isLanguageSupport) {
        speech.speak(message, TextToSpeech.QUEUE_ADD, null, null)
    } else {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
fun addToastSpeech(message: String) {
    val speech: TextToSpeech = KoinPlatform.getKoin().get()
    val isLanguageSupport: Boolean = KoinPlatform.getKoin().get()
    val context: Context = KoinPlatform.getKoin().get()
    if (isLanguageSupport) {
        speech.speak(message, TextToSpeech.QUEUE_ADD, null, null)
    } else {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
suspend fun PointerInputScope.detectSwipe(
    swipeState: MutableState<SwipeType> = mutableStateOf(SwipeType.Normal),
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {},
    onSwipeUp: () -> Unit = {},
    onSwipeDown: () -> Unit = {},
) = detectDragGestures(onDrag = { change, dragAmount ->
    change.consume()
    val (x, y) = dragAmount
    if (abs(x) > abs(y)) {
        when {
            x > 0 -> swipeState.value = SwipeType.SwipeRight
            x < 0 -> swipeState.value = SwipeType.SwipeLeft
        }
    } else {
        when {
            y > 0 -> swipeState.value = SwipeType.SwipeDown
            y < 0 -> swipeState.value = SwipeType.SwipeUp
        }
    }
}, onDragEnd = {
    when (swipeState.value) {
        SwipeType.Normal -> {}
        SwipeType.SwipeRight -> onSwipeRight()
        SwipeType.SwipeLeft -> onSwipeLeft()
        SwipeType.SwipeDown -> onSwipeDown()
        SwipeType.SwipeUp -> onSwipeUp()
    }
})

fun askSpeechInput(
    voiceResult: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    val textToSpeech: TextToSpeech = KoinPlatform.getKoin().get()

    val context: Context = KoinPlatform.getKoin().get()
    textToSpeech.stop()
    if (!SpeechRecognizer.isRecognitionAvailable(context)) {
        showToast(R.string.speech_not_available)
    } else {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.say_something))
        voiceResult.launch(intent)
    }
}