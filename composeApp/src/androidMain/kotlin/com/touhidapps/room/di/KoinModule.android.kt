package com.touhidapps.room.di

import com.touhidapps.room.ae.AppDatabase
import com.touhidapps.room.ae.DbFactoryAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModuleAndroid = module {

    // Ensure that the database is initialized before using it
    single<AppDatabase> {
        DbFactoryAndroid.initDatabase(context = androidContext())
        DbFactoryAndroid.initialize()
    }

}