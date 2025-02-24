package com.touhidapps.room

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.ComposeUIViewController
import com.touhidapps.room.di.appModule
import com.touhidapps.room.di.dbModuleiOS
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatformTools

fun MainViewController() = ComposeUIViewController {

    var isKoinInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val alreadyExists = KoinPlatformTools.defaultContext().getOrNull() != null
        if (!alreadyExists) {
            startKoin {
                modules(dbModuleiOS, appModule)
            }
        }
        isKoinInitialized = true
    }

    if(isKoinInitialized) {
        App()
    }


}