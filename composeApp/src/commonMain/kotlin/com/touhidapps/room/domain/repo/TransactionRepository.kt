package com.touhidapps.room.domain.repo

import com.touhidapps.room.domain.model.Summary
import com.touhidapps.room.domain.model.Transaction

/**
 * One-shot operations use suspend function, for streaming or progress operations use Flow.
 */
interface TransactionRepository {
    suspend fun upsert(transaction: Transaction): Boolean

    suspend fun getAll(): List<Transaction>

    suspend fun delete(transaction: Transaction): Boolean
    suspend fun getSummary(): Summary

}

