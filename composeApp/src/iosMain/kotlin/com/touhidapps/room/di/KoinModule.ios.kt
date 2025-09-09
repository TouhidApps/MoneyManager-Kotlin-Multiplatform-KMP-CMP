package com.touhidapps.room.di

import com.touhidapps.room.ae.AppDatabase
import com.touhidapps.room.ae.DbFactoryIos
import org.koin.dsl.module

val dbModuleiOS = module {

    // Ensure that the database is initialized before using it
    single<AppDatabase> {
        DbFactoryIos.initialize()
    }

}