package com.touhidapps.room.domain.usecase

import com.touhidapps.room.domain.model.Transaction
import com.touhidapps.room.domain.repo.TransactionRepository

class TransactionGetAllUseCase(private val repository: TransactionRepository) {

    suspend operator fun invoke(): List<Transaction> = repository.getAll()

}

class TransactionUpsertUseCase(private val repository: TransactionRepository) {

    suspend operator fun invoke(transaction: Transaction): Boolean = repository.upsert(transaction)

}

class TransactionDeleteUseCase(private val repository: TransactionRepository) {

    suspend operator fun invoke(transaction: Transaction): Boolean = repository.delete(transaction)

}
