package com.coding.meet.thirdeye

import android.app.Application
import com.coding.meet.thirdeye.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        
        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                viewmodelModule,
                appModule,
            )
        }
    }
}