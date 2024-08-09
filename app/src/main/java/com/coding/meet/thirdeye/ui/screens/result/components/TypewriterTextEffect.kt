package com.coding.meet.thirdeye.ui.screens.result.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun TypewriterTextEffect(
    text: String,
    minDelayInMillis: Long = 40,
    maxDelayInMillis: Long = 60,
    minCharacterChunk: Int = 1,
    maxCharacterChunk: Int = 5,
    onEffectCompleted: () -> Unit = {},
    displayTextComposable: @Composable (displayedText: String) -> Unit,
) {
    var displayedText by remember { mutableStateOf("") }

    displayTextComposable(displayedText)
    LaunchedEffect(text) {
        val textLength = text.length
        var endIndex = 0

        while (endIndex < textLength) {
            endIndex = minOf(
                endIndex + Random.nextInt(minCharacterChunk, maxCharacterChunk + 1),
                textLength
            )
            displayedText = text.substring(startIndex = 0, endIndex = endIndex)
            delay(Random.nextLong(minDelayInMillis, maxDelayInMillis))
        }

        onEffectCompleted()
    }
}