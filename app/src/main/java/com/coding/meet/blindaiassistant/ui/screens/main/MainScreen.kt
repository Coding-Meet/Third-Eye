package com.coding.meet.blindaiassistant.ui.screens.main

import android.app.Activity
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.coding.meet.blindaiassistant.R
import com.coding.meet.blindaiassistant.ui.navigation.RouteScreen
import com.coding.meet.blindaiassistant.ui.screens.main.components.BottomSection
import com.coding.meet.blindaiassistant.ui.screens.main.components.ToolsListScreen
import com.coding.meet.blindaiassistant.util.Tools
import com.coding.meet.blindaiassistant.util.addToastSpeech
import com.coding.meet.blindaiassistant.util.showToast
import com.coding.meet.blindaiassistant.viewmodels.MainViewModel
import com.coding.meet.blindaiassistant.ui.navigation.LocalNavControllerProvider

/**
 * Created 28-07-2024 at 06:28 pm
 */


@Composable
fun MainScreen(mainViewModel: MainViewModel) {

    val navController = LocalNavControllerProvider.current
    val voiceResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val results = result.data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                ) as ArrayList<String>
                val voiceText = results[0].trim()
                if (voiceText.isNotEmpty()) {
                    val toolsSelect = Tools.entries.find { tools ->
                        voiceText.contains(tools.name, ignoreCase = true)
                    }
                    if (toolsSelect != null) {
                        toolsDetect(mainViewModel, toolsSelect, navController)
                    } else {
                        showToast(R.string.this_voice_not_available_any_feature)
                        addToastSpeech(R.string.swipe_anywhere_on_the_bottom_of_the_screen_to_activate)
                    }
                } else {
                    showToast(R.string.voice_text_is_empty)
                    addToastSpeech(R.string.swipe_anywhere_on_the_bottom_of_the_screen_to_activate)
                }

            } else {
                showToast(R.string.speech_recognition_failed)
                addToastSpeech(R.string.swipe_anywhere_on_the_bottom_of_the_screen_to_activate)
            }
        }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ToolsListScreen(mainViewModel = mainViewModel)
        BottomSection(voiceResult = voiceResult)
    }
}

fun toolsDetect(
    mainViewModel: MainViewModel,
    toolsSelect: Tools,
    navController: NavController,
) {
    mainViewModel.onToolsSelected(toolsSelect)
    showToast(R.string.open,Toast.LENGTH_SHORT)
    addToastSpeech(toolsSelect.title)
    if (toolsSelect == Tools.DescribeImage || toolsSelect == Tools.ImageToText) {
        navController.navigate(RouteScreen.Camera.route)
    } else {
        mainViewModel.onTakePhoto(null)
        navController.navigate(RouteScreen.Result.route)
    }
}


