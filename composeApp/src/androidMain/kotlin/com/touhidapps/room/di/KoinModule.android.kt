package com.touhidapps.room.di

import com.touhidapps.room.db.AppDatabase
import com.touhidapps.room.db.DbFactoryAndro
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModuleAndroid = module {

    // Ensure that the database is initialized before using it
    single<AppDatabase> {
        DbFactoryAndro.initDatabase(context = androidContext())
        DbFactoryAndro.initialize()
    }

}