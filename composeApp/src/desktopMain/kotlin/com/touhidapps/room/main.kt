package com.touhidapps.room

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.touhidapps.room.di.appModule
import com.touhidapps.room.di.dbModuleJvm
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatformTools

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MoneyManager",
    ) {


        var isKoinInitialized by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            val alreadyExists = KoinPlatformTools.defaultContext().getOrNull() != null
            if (!alreadyExists) {
                startKoin {
                    modules(dbModuleJvm, appModule)
                }
            }
            isKoinInitialized = true
        }
        if (isKoinInitialized) {
            App()
        }

    }
}


