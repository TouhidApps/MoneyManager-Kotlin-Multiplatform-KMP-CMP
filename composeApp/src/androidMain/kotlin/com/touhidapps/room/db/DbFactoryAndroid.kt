package com.touhidapps.room.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.touhidapps.room.data.datasource.local.db.AppDatabase
import com.touhidapps.room.data.datasource.local.db.dbFileName
import kotlinx.coroutines.Dispatchers

object DbFactoryAndroid: RoomDatabaseConstructor<AppDatabase> {

    lateinit var appContext: Context

    fun initDatabase(context: Context) {
        appContext = context.applicationContext
    }

    override fun initialize(): AppDatabase {

        if (!::appContext.isInitialized) {
            throw IllegalStateException("Context not initialized. Call initDatabase(context) first.")
        }

        val dbFile = appContext.getDatabasePath(dbFileName)
        return Room.databaseBuilder<AppDatabase>(
            context = appContext.applicationContext,
            name = dbFile.absolutePath
        )
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()

    }



}