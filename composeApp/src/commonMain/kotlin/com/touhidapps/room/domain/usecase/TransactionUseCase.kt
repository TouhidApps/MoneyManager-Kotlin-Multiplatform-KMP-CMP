package com.touhidapps.room.domain.usecase

import com.touhidapps.room.domain.model.Transaction
import com.touhidapps.room.domain.repo.TransactionRepository

class TransactionUpsertUseCase(private val repository: TransactionRepository) {

    suspend operator fun invoke(transaction: Transaction): Boolean = repository.upsert(transaction)

}

// Business logic implementaion example
//class TransactionUpsertUseCase(private val repository: TransactionRepository) {
//    suspend operator fun invoke(transaction: Transaction): Boolean {
//        if (transaction.amount < 0) {
//            throw IllegalArgumentException("Transaction amount can not be negative.")
//        }
//        return repository.upsert(transaction)
//    }
//}


class TransactionGetAllUseCase(private val repository: TransactionRepository) {

    suspend operator fun invoke(): List<Transaction> = repository.getAll()

}

// Business logic example
//class TransactionGetAllUseCase(private val repository: TransactionRepository) {
//    suspend operator fun invoke(): List<Transaction> {
//        val mData = repository.getAll().filter { it.amount != 0.0 }
//        return mData
//    }
//}

class TransactionDeleteUseCase(private val repository: TransactionRepository) {

    suspend operator fun invoke(transaction: Transaction): Boolean = repository.delete(transaction)

}
