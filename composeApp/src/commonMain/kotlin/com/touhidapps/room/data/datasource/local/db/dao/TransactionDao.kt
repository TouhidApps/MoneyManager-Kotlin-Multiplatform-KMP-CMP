package com.touhidapps.room.data.datasource.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.touhidapps.room.data.datasource.local.db.entity.SummaryEntity
import com.touhidapps.room.data.datasource.local.db.entity.TransactionEntity
import com.touhidapps.room.domain.model.Summary

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

    @Query("""
        SELECT 
            t.totalIncome,
            t.totalExpense,
            t.totalIncome - t.totalExpense AS balance
        FROM (
            SELECT 
                COALESCE(SUM(CASE WHEN isIncome = 1 THEN amount END), 0.0) AS totalIncome,
                COALESCE(SUM(CASE WHEN isIncome = 0 THEN amount END), 0.0) AS totalExpense
            FROM MyTransaction
        ) t
    """)
    suspend fun getSummary(): SummaryEntity

}

