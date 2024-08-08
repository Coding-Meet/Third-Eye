package com.coding.meet.blindaiassistant.ui.screens.instruction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coding.meet.blindaiassistant.R
import com.coding.meet.blindaiassistant.ui.navigation.LocalNavControllerProvider
import com.coding.meet.blindaiassistant.util.addToastSpeech
import com.coding.meet.blindaiassistant.util.detectSwipe
import com.coding.meet.blindaiassistant.util.pauseVoice
import com.coding.meet.blindaiassistant.util.showToast

/**
 * Created 08-08-2024 at 04:33 pm
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionScreen() {
    val navController = LocalNavControllerProvider.current
    val instructionTxt by remember {
        mutableStateOf(
            "There are four options for you.\n" +
                    "First is Custom Prompt.\n" +
                    "Second is custom prompt with image.\n" +
                    "Third is describe image.\n" +
                    "And fourth is image to text."
        )
    }
    LaunchedEffect(Unit) {
        showToast(instructionTxt)
        addToastSpeech(R.string.swipe_right_to_home_screen)
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(R.string.instruction))
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pointerInput(Unit) {
                    detectSwipe(
                        onSwipeRight = {
                            pauseVoice()
                            navController.navigateUp()
                        },
                    )
                },
            verticalArrangement = Arrangement.spacedBy(16.dp),

            ) {
            Text(
                text = instructionTxt,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }
    }
} 