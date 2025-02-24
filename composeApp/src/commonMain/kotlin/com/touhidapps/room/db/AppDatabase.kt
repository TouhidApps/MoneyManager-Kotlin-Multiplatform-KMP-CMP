package com.touhidapps.room.db


import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.touhidapps.room.db.dao.MyTransactionDao
import com.touhidapps.room.db.entiry.MyTransaction


internal const val dbFileName = "transaction.db"

@Database(
    entities = [MyTransaction::class],
    version = 1
)
 // Uses platform-specific DbFactory for instantiation
@ConstructedBy(DbFactory::class) // don't use actual(keyword) reference of DbFactory object as it is used with ConstructedBy
abstract class AppDatabase: RoomDatabase() {

    abstract fun transDao(): MyTransactionDao

}

