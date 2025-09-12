package com.touhidapps.room.data.datasource.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.touhidapps.room.data.datasource.local.db.entity.TransactionEntity

@Dao
interface TransactionDao {

    @Upsert
    suspend fun upsert(transaction: TransactionEntity)

    @Query("SELECT * FROM MyTransaction ORDER BY transactionTimeStamp ASC")
    suspend fun getAllTransaction(): List<TransactionEntity>

    @Query("DELETE FROM MyTransaction")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(transaction: TransactionEntity)

}