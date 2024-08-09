package com.coding.meet.thirdeye.di


import android.content.Context
import android.speech.tts.TextToSpeech
import com.coding.meet.thirdeye.R
import com.coding.meet.thirdeye.util.showToast
import org.koin.dsl.module
import org.koin.mp.KoinPlatform
import java.util.Locale

val appModule = module {
    val context: Context = KoinPlatform.getKoin().get()
    lateinit var textToSpeech: TextToSpeech
    var isLanguageSupport = true
    textToSpeech = TextToSpeech(
        context
    ) { status ->
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.getDefault())
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                isLanguageSupport = false
                showToast(R.string.language_is_not_supported)
                return@TextToSpeech
            }
            isLanguageSupport = true
        } else if (status == TextToSpeech.ERROR) {
            isLanguageSupport = false
            showToast(R.string.error_text_to_speech)
        }
    }
    single { textToSpeech }
    single {
        isLanguageSupport
    }
}
