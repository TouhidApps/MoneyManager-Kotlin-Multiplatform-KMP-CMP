package com.touhidapps.room

import android.app.Application
import com.touhidapps.room.di.appModule
import com.touhidapps.room.di.dbModuleAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp) // Required for Android

            modules(
                dbModuleAndroid, // load android specific modules
                appModule // Load common Koin modules
            )

        } // startKoin

    }

}