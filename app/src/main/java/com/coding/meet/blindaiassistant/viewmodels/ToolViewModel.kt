package com.coding.meet.blindaiassistant.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.meet.blindaiassistant.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

/**
 * Created 28-07-2024 at 05:08 pm
 */

class ToolViewModel : ViewModel() {
    private val geminiModel = GenerativeModel(
        modelName = "gemini-1.5-flash", apiKey = BuildConfig.Gemini_API_KEY
    )

    fun generateContent(
        prompt: String,
        resultFun: (String) -> Unit,
        errorFun: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response = geminiModel.generateContent(
                    "Please provide a response in plain text format, without any markdown or special formatting. The response should be clear, concise, and easy to read. :$prompt")
                resultFun(response.text.toString())
            } catch (e: Exception) {
                errorFun(e.message.toString())
            }
        }
    }

    fun generateContent(
        originalBitmap: Bitmap,
        prompt: String,
        resultFun: (String) -> Unit,
        errorFun: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val inputContent = content {
                    image(originalBitmap)
                    text(prompt)
                }
                resultFun(geminiModel.generateContent(inputContent).text.toString())
            } catch (e: Exception) {
                errorFun(e.message.toString())
            }
        }
    }
}