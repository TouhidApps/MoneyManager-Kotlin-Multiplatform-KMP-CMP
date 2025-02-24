package com.touhidapps.room.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.touhidapps.room.db.entiry.MyTransaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MyTransactionDao {

    @Upsert
    suspend fun upsert(myTransaction: MyTransaction)

    @Query("SELECT * FROM MyTransaction")
    fun getAllTransaction(): Flow<List<MyTransaction>> // flow for realtime update

    @Query("DELETE FROM MyTransaction")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(myTransaction: MyTransaction)

}