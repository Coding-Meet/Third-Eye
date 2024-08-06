package com.coding.meet.blindaiassistant

import android.app.Application
import com.coding.meet.blindaiassistant.di.*
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
                networkModule,
                databaseModule,
                repositoryModule,
                viewmodelModule,
                appModule,
            )
        }
    }
}