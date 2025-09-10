package com.touhidapps.room.db

import androidx.room.Room
import androidx.room.RoomDatabaseConstructor
import com.touhidapps.room.data.datasource.local.db.AppDatabase
import com.touhidapps.room.data.datasource.local.db.dbFileName
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

object DbFactoryIos: RoomDatabaseConstructor<AppDatabase> {

    override fun initialize(): AppDatabase {
        val dbFile = documentDirectory() + "/" + dbFileName

        return Room.databaseBuilder<AppDatabase>(
            name = dbFile
        )
            .setDriver(androidx.sqlite.driver.bundled.BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.Companion.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path)
    }



}