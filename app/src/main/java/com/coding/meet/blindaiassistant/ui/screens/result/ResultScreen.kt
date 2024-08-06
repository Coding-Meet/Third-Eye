package com.coding.meet.blindaiassistant.ui.screens.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.coding.meet.blindaiassistant.R
import com.coding.meet.blindaiassistant.ui.navigation.LocalNavControllerProvider
import com.coding.meet.blindaiassistant.ui.screens.result.components.ChatBubbleItem
import com.coding.meet.blindaiassistant.util.Tools
import com.coding.meet.blindaiassistant.util.addToastSpeech
import com.coding.meet.blindaiassistant.util.detectSwipe
import com.coding.meet.blindaiassistant.util.pauseVoice
import com.coding.meet.blindaiassistant.viewmodels.MainViewModel
import com.coding.meet.blindaiassistant.viewmodels.ToolViewModel

/**
 * Created 31-07-2024 at 10:40 am
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(mainViewModel: MainViewModel, toolViewModel: ToolViewModel) {
    val bitmaps by mainViewModel.bitmaps.collectAsState()
    val currentTools by mainViewModel.currentTools.collectAsState()
    val context = LocalContext.current
    var answerTxt by remember {
        mutableStateOf<String?>(null)
    }
    DisposableEffect(key1 = Unit) {
        onDispose {
            pauseVoice()
        }
    }
    val navController = LocalNavControllerProvider.current

    LaunchedEffect(
        currentTools
    ) {
        addToastSpeech(R.string.few_seconds_wait_for_response)
        mainViewModel.setLoading(true)
        if (currentTools == Tools.DescribeImage || currentTools == Tools.ImageToText) {
            toolViewModel.generateContent(
                originalBitmap = bitmaps!!,
                prompt = context.getString(currentTools.prompt),
                resultFun = {
                    mainViewModel.setLoading(false)
                    answerTxt = it
                    addToastSpeech(it)
                    addToastSpeech(R.string.swipe_right_to_home_screen)
                },
                errorFun = {
                    mainViewModel.setLoading(false)
                    answerTxt = it
                    addToastSpeech(it)
                    addToastSpeech(R.string.swipe_right_to_home_screen)

                })
        } else {
            toolViewModel.generateContent(
                prompt = context.getString(currentTools.prompt),
                resultFun = {
                    mainViewModel.setLoading(false)
                    answerTxt = it
                    addToastSpeech(it)
                    addToastSpeech(R.string.swipe_right_to_home_screen)
                },
                errorFun = {
                    mainViewModel.setLoading(false)
                    answerTxt = it
                    addToastSpeech(it)
                    addToastSpeech(R.string.swipe_right_to_home_screen)
                })
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = currentTools.title))
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "arrow"
                        )
                    }
                })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pointerInput(Unit) {
                    detectSwipe(
                        onSwipeRight = {
                            navController.navigateUp()
                        },
                    )
                },
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                ChatBubbleItem(currentTools, bitmaps, null)
            }
            answerTxt?.let {
                item {
                    ChatBubbleItem(currentTools, bitmaps, it)
                }
            }
        }
    }
}
