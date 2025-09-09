package com.touhidapps.room.domain.usecase

import com.touhidapps.room.domain.model.MyTransaction
import com.touhidapps.room.domain.repo.TransactionRepository

class TransactionGetAllUseCase(private val repository: TransactionRepository) {

    suspend operator fun invoke(): List<MyTransaction> = repository.getAll()

}

class TransactionUpsertUseCase(private val repository: TransactionRepository) {

    suspend operator fun invoke(myTransaction: MyTransaction): Boolean = repository.upsert(myTransaction)

}

class TransactionDeleteUseCase(private val repository: TransactionRepository) {

    suspend operator fun invoke(myTransaction: MyTransaction): Boolean = repository.delete(myTransaction)

}
