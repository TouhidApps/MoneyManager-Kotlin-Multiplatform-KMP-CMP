package com.touhidapps.room.repository


import com.touhidapps.room.db.dao.MyTransactionDao
import com.touhidapps.room.db.entiry.MyTransaction
import kotlinx.coroutines.flow.Flow

class TransactionRepository(private var mDao: MyTransactionDao) {

    suspend fun upsert(myTransaction: MyTransaction): Boolean {

        return try {
            mDao.upsert(myTransaction)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

    fun getAll(): Flow<List<MyTransaction>> = mDao.getAllTransaction()

    suspend fun deleteAll(): Boolean {

        return try {
            mDao.deleteAll()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

    suspend fun delete(myTransaction: MyTransaction): Boolean {

        return try {
            mDao.delete(myTransaction)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

}