package com.coding.meet.thirdeye.ui.screens.home.components

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coding.meet.thirdeye.R
import com.coding.meet.thirdeye.ui.navigation.LocalNavControllerProvider
import com.coding.meet.thirdeye.ui.navigation.RouteScreen
import com.coding.meet.thirdeye.ui.theme.boxBackgroundColor
import com.coding.meet.thirdeye.ui.theme.boxBorderColor
import com.coding.meet.thirdeye.ui.theme.textColor
import com.coding.meet.thirdeye.util.askSpeechInput
import com.coding.meet.thirdeye.util.detectSwipe

/**
 * Created 29-07-2024 at 12:12 pm
 */

@Composable
fun ColumnScope.BottomSection(
    toolVoiceResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
) {
    val navController = LocalNavControllerProvider.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .weight(0.3f)
            .pointerInput(Unit) {
                detectSwipe(
                    onSwipeUp = {
                        navController.navigate(RouteScreen.Instruction.route)
//                        showToast(R.string.swipe_up)
                    },
                    onSwipeDown = {
                        askSpeechInput(toolVoiceResult)
//                        showToast(R.string.swipe_down)
                    },
                    onSwipeLeft = {
                        askSpeechInput(toolVoiceResult)
//                        showToast(R.string.swipe_left)
                    },
                    onSwipeRight = {
                        askSpeechInput(toolVoiceResult)
//                        showToast(R.string.swipe_right)
                    },
                )
            }
            .border(5.dp, boxBorderColor, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(boxBackgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.swipe_here),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 35.sp,
            color = textColor,
            fontSize = 40.sp)
    }
}