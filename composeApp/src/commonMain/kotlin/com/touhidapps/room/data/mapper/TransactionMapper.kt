package com.touhidapps.room.data.mapper

import com.touhidapps.room.data.datasource.local.db.entity.TransactionEntity
import com.touhidapps.room.domain.model.Transaction


fun TransactionEntity.toDomain(): Transaction = Transaction(
    id = id,
    title = title,
    amount = amount,
    isIncome = isIncome,
    transactionTimeStamp = transactionTimeStamp,
    entryTimeStamp = entryTimeStamp
)

//Use Entity for DB, Dto for network. for postfix and also for mapping toDto/toEntity
fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        title = title,
        amount = amount,
        isIncome = isIncome,
        transactionTimeStamp = transactionTimeStamp,
        entryTimeStamp = entryTimeStamp
    )
}

