package com.touhidapps.room.di

import com.touhidapps.room.db.AppDatabase
import com.touhidapps.room.db.DbFactoryiOS
import org.koin.dsl.module

val dbModuleiOS = module {

    // Ensure that the database is initialized before using it
    single<AppDatabase> {
        DbFactoryiOS.initialize()
    }

}