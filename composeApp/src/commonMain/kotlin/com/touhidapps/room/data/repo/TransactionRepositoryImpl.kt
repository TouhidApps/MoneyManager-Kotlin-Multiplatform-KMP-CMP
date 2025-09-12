package com.touhidapps.room.data.repo

import com.touhidapps.room.data.datasource.local.db.dao.TransactionDao
import com.touhidapps.room.data.mapper.toDomain
import com.touhidapps.room.data.mapper.toEntity
import com.touhidapps.room.domain.model.Transaction
import com.touhidapps.room.domain.repo.TransactionRepository

class TransactionRepositoryImpl(private var mDao: TransactionDao): TransactionRepository {

    override suspend fun upsert(transaction: Transaction): Boolean {

        return try {
            mDao.upsert(transaction.toEntity())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

    override suspend fun getAll(): List<Transaction> {
        return mDao.getAllTransaction().map { it.toDomain() }
    }

    override suspend fun delete(transaction: Transaction): Boolean {

        return try {
            mDao.delete(transaction.toEntity())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

}