package com.coding.meet.blindaiassistant.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.coding.meet.blindaiassistant.viewmodels.*
val viewmodelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::ToolViewModel)
}