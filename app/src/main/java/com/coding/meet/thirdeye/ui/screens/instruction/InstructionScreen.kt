package com.coding.meet.thirdeye.ui.screens.instruction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coding.meet.thirdeye.R
import com.coding.meet.thirdeye.ui.navigation.LocalNavControllerProvider
import com.coding.meet.thirdeye.ui.theme.boxBorderColor
import com.coding.meet.thirdeye.ui.theme.mainBackgroundColor
import com.coding.meet.thirdeye.ui.theme.textColor
import com.coding.meet.thirdeye.util.addToastSpeech
import com.coding.meet.thirdeye.util.detectSwipe
import com.coding.meet.thirdeye.util.pauseVoice
import com.coding.meet.thirdeye.util.showToast
import dev.jeziellago.compose.markdowntext.MarkdownText

/**
 * Created 08-08-2024 at 04:33 pm
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionScreen() {
    val navController = LocalNavControllerProvider.current
    val appName = stringResource(id = R.string.app_name)
    val voiceInstructionTxt by remember {
        mutableStateOf(
            "$appName is an innovative Android application designed to assist blind people by leveraging the power of Gemini AI. The app provides advanced functionalities to enhance accessibility and support daily tasks through voice commands and AI-powered features. It provides four main functionalities:\n"+
                    "1. Custom Prompt\n" +
                    "Purpose: Allows you to enter a custom query or instruction.\n" +
                    "2. Custom Prompt with Image\n" +
                    "Purpose: Combine a custom prompt with an image for more context-specific responses.\n" +
                    "3. Describe Image\n" +
                    "Purpose: Provides you a textual description of an uploaded or captured image.\n" +
                    "4. Image to Text\n" +
                    "Purpose: Extracts and displays text from an image.\n"
        )
    }
    LaunchedEffect(Unit) {
        showToast(voiceInstructionTxt)
        addToastSpeech(R.string.swipe_right_to_home_screen)
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
                        text = stringResource(R.string.instruction),
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
        LazyColumn(
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                MarkdownText(
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                    ),
                    markdown = """
                        ${stringResource(id = R.string.app_name)} is an innovative Android application designed to assist blind people by leveraging the power of Gemini AI. The app provides advanced functionalities to enhance accessibility and support daily tasks through voice commands and AI-powered features. It provides four main functionalities:

                        1. **Custom Prompt**
                        2. **Custom Prompt with Image**
                        3. **Describe Image**
                        4. **Image to Text**

                        ## Features

                        ### 1. Custom Prompt

                        - **Purpose**: Allows you to enter a custom query or instruction.
                        - **How to Use**:
                          1. Open the app and navigate to the custom prompt input section.
                          2. Enter your custom instruction or query in the provided text box.
                          3. Submit the prompt by pressing the "Submit" button.
                          4. The app will process your input and provide a response based on your query.

                        ### 2. Custom Prompt with Image

                        - **Purpose**: Combine a custom prompt with an image for more context-specific responses.
                        - **How to Use**:
                          1. Open the app and go to the custom prompt and image upload section.
                          2. Enter your custom instruction or query in the text box.
                          3. Upload or take a photo by selecting the image upload option.
                          4. Submit both the prompt and image by pressing the "Submit" button.
                          5. The app will analyze the image and the prompt to generate a response that considers both elements.

                        ### 3. Describe Image

                        - **Purpose**: Provides you a textual description of an uploaded or captured image.
                        - **How to Use**:
                          1. Open the app and navigate to the image description feature.
                          2. Upload or capture an image using the provided options.
                          3. Submit the image for processing.
                          4. The app will analyze the image and generate a descriptive text summarizing its content.

                        ### 4. Image to Text

                        - **Purpose**: Extracts and displays text from an image.
                        - **How to Use**:
                          1. Open the app and go to the image-to-text conversion section.
                          2. Upload or capture an image that contains text.
                          3. Submit the image for text extraction.
                          4. The app will process the image and provide the extracted text for you to view.

                    """.trimIndent(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
            }
        }
    }
} 