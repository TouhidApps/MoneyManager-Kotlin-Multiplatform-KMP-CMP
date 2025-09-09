package com.touhidapps.room.data.datasource.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.touhidapps.room.data.datasource.local.db.entity.MyTransactionEntity

@Dao
interface MyTransactionDao {

    @Upsert
    suspend fun upsert(myTransaction: MyTransactionEntity)

    @Query("SELECT * FROM MyTransaction ORDER BY transactionTimeStamp ASC")
    suspend fun getAllTransaction(): List<MyTransactionEntity>

    @Query("DELETE FROM MyTransaction")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(myTransaction: MyTransactionEntity)

}