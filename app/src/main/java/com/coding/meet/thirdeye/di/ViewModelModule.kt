package com.coding.meet.thirdeye.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.coding.meet.thirdeye.viewmodels.*

val viewmodelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::ToolViewModel)
}