package com.example.cleanarchitecturesolution.core.app

import android.app.Application
import com.example.cleanarchitecturesolution.core.di.koinInjector
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin()
    }

    private fun startKoin() {
        startKoin {
            androidContext(this@MyApplication)
            modules(koinInjector)
        }
    }
}