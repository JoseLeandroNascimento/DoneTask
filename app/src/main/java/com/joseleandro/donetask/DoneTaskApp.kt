package com.joseleandro.donetask

import android.app.Application
import com.joseleandro.donetask.core.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DoneTaskApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@DoneTaskApp)
            modules(uiModule)
        }
    }
}