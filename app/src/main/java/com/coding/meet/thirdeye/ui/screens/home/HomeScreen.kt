package com.coding.meet.thirdeye.ui.screens.home

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.coding.meet.thirdeye.R
import com.coding.meet.thirdeye.ui.navigation.LocalNavControllerProvider
import com.coding.meet.thirdeye.ui.navigation.RouteScreen
import com.coding.meet.thirdeye.ui.screens.home.components.BottomSection
import com.coding.meet.thirdeye.ui.screens.home.components.ToolsListScreen
import com.coding.meet.thirdeye.ui.theme.mainBackgroundColor
import com.coding.meet.thirdeye.util.CAMERAX_PERMISSIONS
import com.coding.meet.thirdeye.util.Tools
import com.coding.meet.thirdeye.util.addToastSpeech
import com.coding.meet.thirdeye.util.pauseVoice
import com.coding.meet.thirdeye.util.showToast
import com.coding.meet.thirdeye.viewmodels.MainViewModel
import kotlinx.coroutines.delay

/**
 * Created 28-07-2024 at 06:28 pm
 */


@Composable
fun HomeScreen(mainViewModel: MainViewModel, activity: Activity) {
    LaunchedEffect(Unit) {
        delay(500)
        if (!hasRequiredPermissions(activity.applicationContext)) {
            ActivityCompat.requestPermissions(
                activity, CAMERAX_PERMISSIONS, 0
            )
            delay(500)
            showToast(R.string.camera_permission_request)
            addToastSpeech(R.string.swipe_anywhere_on_the_bottom_of_the_screen_to_activate)
            addToastSpeech(R.string.swipe_up_to_get_instructions_on_how_to_use_the_app)
        } else {
            showToast(R.string.swipe_anywhere_on_the_bottom_of_the_screen_to_activate)
            addToastSpeech(R.string.swipe_up_to_get_instructions_on_how_to_use_the_app)
        }
    }
    val context = LocalContext.current
    val navController = LocalNavControllerProvider.current
    val toolVoiceResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val results = result.data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                ) as ArrayList<String>
                val voiceText = results[0].trim()
                if (voiceText.isNotEmpty()) {
                    val toolsSelect = Tools.entries.find { tools ->
                        voiceText.contains(context.getString(tools.title), ignoreCase = true)
                    }
                    if (toolsSelect != null) {
                        toolsDetect(mainViewModel, toolsSelect, navController)
                    } else {
                        showToast(R.string.this_voice_not_available_any_feature)
                        addToastSpeech(R.string.swipe_anywhere_on_the_bottom_of_the_screen_to_activate)
                        addToastSpeech(R.string.swipe_up_to_get_instructions_on_how_to_use_the_app)
                    }
                } else {
                    showToast(R.string.voice_text_is_empty)
                    addToastSpeech(R.string.swipe_anywhere_on_the_bottom_of_the_screen_to_activate)
                    addToastSpeech(R.string.swipe_up_to_get_instructions_on_how_to_use_the_app)
                }
            } else {
                showToast(R.string.speech_recognition_failed)
                addToastSpeech(R.string.swipe_anywhere_on_the_bottom_of_the_screen_to_activate)
                addToastSpeech(R.string.swipe_up_to_get_instructions_on_how_to_use_the_app)
            }
        }
    Scaffold(containerColor = mainBackgroundColor) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ToolsListScreen(mainViewModel = mainViewModel)
            BottomSection(toolVoiceResult = toolVoiceResult)
        }
    }
}

fun toolsDetect(
    mainViewModel: MainViewModel,
    toolsSelect: Tools,
    navController: NavController,
) {
    mainViewModel.onToolsSelected(toolsSelect)
    pauseVoice()
//    showToast(messageID1 = R.string.open, messageID2 = toolsSelect.title)
    mainViewModel.onTakePhoto(null)
    if (toolsSelect == Tools.DescribeImage || toolsSelect == Tools.ImageToText) {
        navController.navigate(RouteScreen.Camera.route)
    } else {
        mainViewModel.setCurrentPrompt("")
        navController.navigate(RouteScreen.Prompt.route)
    }
}

private fun hasRequiredPermissions(applicationContext: Context): Boolean {
    return CAMERAX_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            applicationContext,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }
}


