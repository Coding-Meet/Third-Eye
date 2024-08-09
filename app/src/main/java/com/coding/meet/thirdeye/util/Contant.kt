package com.coding.meet.thirdeye.util

import androidx.compose.animation.core.tween
import androidx.compose.animation.*


/**
 * Created 28-07-2024 at 06:44 pm
 */



enum class SwipeType {
    Normal,
    SwipeRight,
    SwipeLeft,
    SwipeDown,
    SwipeUp
}

val CAMERAX_PERMISSIONS = arrayOf(
    android.Manifest.permission.CAMERA
)
val FadeIn = fadeIn(animationSpec = tween(220, delayMillis = 90)) +
        scaleIn(
            initialScale = 0.92f,
            animationSpec = tween(220, delayMillis = 90)
        )

val FadeOut = fadeOut(animationSpec = tween(90))