package com.touhidapps.room.domain.repo

import com.touhidapps.room.domain.model.MyTransaction

/**
 * One-shot operations use suspend function, for streaming or progress operations use Flow.
 */
interface TransactionRepository {
    suspend fun upsert(myTransaction: MyTransaction): Boolean

    suspend fun getAll(): List<MyTransaction>

    suspend fun delete(myTransaction: MyTransaction): Boolean

}

