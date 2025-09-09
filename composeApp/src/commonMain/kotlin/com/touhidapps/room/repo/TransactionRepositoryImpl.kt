package com.touhidapps.room.repo

import com.touhidapps.room.data.datasource.local.db.dao.MyTransactionDao
import com.touhidapps.room.data.mapper.toDomain
import com.touhidapps.room.data.mapper.toEntity
import com.touhidapps.room.domain.model.MyTransaction
import com.touhidapps.room.domain.repo.TransactionRepository

class TransactionRepositoryImpl(private var mDao: MyTransactionDao): TransactionRepository {

    override suspend fun upsert(myTransaction: MyTransaction): Boolean {

        return try {
            mDao.upsert(myTransaction.toEntity())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

    override suspend fun getAll(): List<MyTransaction> {
        return mDao.getAllTransaction().map { it.toDomain() }
    }

    override suspend fun delete(myTransaction: MyTransaction): Boolean {

        return try {
            mDao.delete(myTransaction.toEntity())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

}