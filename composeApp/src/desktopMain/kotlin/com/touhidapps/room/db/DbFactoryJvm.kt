package com.touhidapps.room.db

import androidx.room.Room
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.touhidapps.room.data.datasource.local.db.AppDatabase
import com.touhidapps.room.data.datasource.local.db.dbFileName
import kotlinx.coroutines.Dispatchers
import java.io.File

object DbFactoryJvm : RoomDatabaseConstructor<AppDatabase> {

     override fun initialize(): AppDatabase {
        val dbFile = File(System.getProperty("java.io.tmpdir"), dbFileName)

        return Room.databaseBuilder<AppDatabase>(
            name = dbFile.absolutePath,
        )
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

}