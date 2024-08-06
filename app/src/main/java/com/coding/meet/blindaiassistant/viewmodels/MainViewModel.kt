package com.coding.meet.blindaiassistant.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.coding.meet.blindaiassistant.util.Tools
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created 29-07-2024 at 11:21 am
 */

class MainViewModel : ViewModel() {


    private val _isLoadingStateFlow = MutableStateFlow(false)
    val isLoadingStateFlow = _isLoadingStateFlow.asStateFlow()


    private val _bitmaps = MutableStateFlow<Bitmap?>(null)
    val bitmaps = _bitmaps.asStateFlow()

    private val _currentTools = MutableStateFlow(Tools.DescribeImage)
    val currentTools = _currentTools.asStateFlow()


    fun onToolsSelected(tools: Tools) {
        _currentTools.update {
            tools
        }
    }

    fun onTakePhoto(bitmap: Bitmap?) {
        _bitmaps.update {
            bitmap
        }
    }

    fun setLoading(value: Boolean) {
        _isLoadingStateFlow.update {
            value
        }
    }
}