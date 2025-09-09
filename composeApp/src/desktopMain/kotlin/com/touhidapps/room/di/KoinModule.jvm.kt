package com.touhidapps.room.di

import com.touhidapps.room.ae.AppDatabase
import com.touhidapps.room.ae.DbFactoryJvm
import org.koin.dsl.module

val dbModuleJvm = module {

    // Ensure that the database is initialized before using it
    single<AppDatabase> {
        DbFactoryJvm.initialize()
    }

}