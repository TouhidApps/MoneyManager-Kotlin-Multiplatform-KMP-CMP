package com.touhidapps.room.data.datasource.local.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.touhidapps.room.data.datasource.local.db.dao.TransactionDao
import com.touhidapps.room.data.datasource.local.db.entity.TransactionEntity

internal const val dbFileName = "transaction.db"

@Database(
    entities = [TransactionEntity::class],
    version = 1
)
 // Uses platform-specific DbFactory for instantiation
@ConstructedBy(DbFactory::class) // don't use actual(keyword) reference of DbFactory object as it is used with ConstructedBy
abstract class AppDatabase: RoomDatabase() {

    abstract fun transDao(): TransactionDao

}

