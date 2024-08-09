package com.coding.meet.thirdeye.ui.screens.prompt_write

import android.app.Activity
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.coding.meet.thirdeye.R
import com.coding.meet.thirdeye.ui.navigation.LocalNavControllerProvider
import com.coding.meet.thirdeye.ui.navigation.RouteScreen
import com.coding.meet.thirdeye.ui.theme.boxBorderColor
import com.coding.meet.thirdeye.ui.theme.mainBackgroundColor
import com.coding.meet.thirdeye.ui.theme.textColor
import com.coding.meet.thirdeye.util.Tools
import com.coding.meet.thirdeye.util.addToastSpeech
import com.coding.meet.thirdeye.util.askSpeechInput
import com.coding.meet.thirdeye.util.detectSwipe
import com.coding.meet.thirdeye.util.pauseVoice
import com.coding.meet.thirdeye.util.showToast
import com.coding.meet.thirdeye.viewmodels.MainViewModel

/**
 * Created 06-08-2024 at 06:59 pm
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptScreen(mainViewModel: MainViewModel) {
    val currentTools by mainViewModel.currentTools.collectAsState()
    val currentPrompt by mainViewModel.currentPrompt.collectAsState()


    LaunchedEffect(Unit) {
        speakVoice(currentTools)
    }
    val navController = LocalNavControllerProvider.current

    val promptVoiceResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val results = result.data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                ) as ArrayList<String>
                val voiceText = results[0].trim()
                if (voiceText.isNotEmpty()) {
                    mainViewModel.setCurrentPrompt(voiceText)
                    speakVoice(currentTools)
                } else {
                    showToast(R.string.voice_text_is_empty)
                    speakVoice(currentTools)
                }

            } else {
                showToast(R.string.speech_recognition_failed)
                speakVoice(currentTools)
            }
        }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = boxBorderColor,
                    titleContentColor = textColor,
                    navigationIconContentColor = textColor,
                ),
                title = {
                    Text(
                        text = stringResource(id = currentTools.title),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        pauseVoice()
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "arrow"
                        )
                    }
                })
        },
        containerColor = mainBackgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pointerInput(Unit) {
                    detectSwipe(
                        onSwipeLeft = {
                            askSpeechInput(promptVoiceResult)
                        },
                        onSwipeUp = {
                            validateResult(currentPrompt, currentTools, navController)
                        },
                        onSwipeRight = {
                            pauseVoice()
                            navController.navigateUp()
                        },
                    )
                }
            ) {
            OutlinedTextField(
                label = { Text(stringResource(R.string.enter_the_prompt)) },
                maxLines = 5,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = boxBorderColor,
                    unfocusedBorderColor = boxBorderColor,
                    focusedLabelColor = boxBorderColor,
                    unfocusedLabelColor = boxBorderColor,
                    cursorColor = boxBorderColor,
                    focusedTextColor = boxBorderColor,
                    unfocusedTextColor = boxBorderColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                ),
                value = currentPrompt, onValueChange = {
                    mainViewModel.setCurrentPrompt(it)
                })

            Button(
                border = BorderStroke(5.dp, boxBorderColor),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    validateResult(currentPrompt, currentTools, navController)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = boxBorderColor,
                    contentColor = textColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = 16.dp),
            ) {
                Text(text = stringResource(R.string.submit),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center, fontSize = 26.sp,
                    lineHeight = 35.sp,
                    color = textColor)
            }
        }
    }
}

fun validateResult(currentPrompt: String, currentTools: Tools, navController: NavController) {
    if (currentPrompt.trim().isEmpty()) {
        showToast(R.string.please_enter_prompt)
        speakVoice(currentTools)
    } else {
        pauseVoice()
        if (currentTools == Tools.CustomPromptImage) {
            navController.navigate(RouteScreen.Camera.route)
        } else {
            navController.navigate(RouteScreen.Result.route) {
                popUpTo(RouteScreen.Prompt.route) { inclusive = true }
            }
        }
    }
}

fun speakVoice(currentTools: Tools) {
    addToastSpeech(R.string.swipe_left_to_speak_prompt)
    if (currentTools == Tools.CustomPrompt) {
        addToastSpeech(R.string.swipe_up_move_result_screen)
    } else {
        addToastSpeech(R.string.swipe_up_move_camera_to_capture_image)
    }
    addToastSpeech(R.string.swipe_right_to_home_screen)
}

