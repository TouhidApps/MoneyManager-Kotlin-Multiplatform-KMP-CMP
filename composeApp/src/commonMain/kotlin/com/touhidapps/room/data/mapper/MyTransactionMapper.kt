package com.touhidapps.room.data.mapper

import com.touhidapps.room.data.datasource.local.db.entity.MyTransactionEntity
import com.touhidapps.room.domain.model.MyTransaction


fun MyTransactionEntity.toDomain(): MyTransaction = MyTransaction(
    id = id,
    title = title,
    amount = amount,
    isIncome = isIncome,
    transactionTimeStamp = transactionTimeStamp,
    entryTimeStamp = entryTimeStamp
)

//Use Entity for DB, Dto for network. for postfix and also for mapping toDto/toEntity
fun MyTransaction.toEntity(): MyTransactionEntity {
    return MyTransactionEntity(
        id = id,
        title = title,
        amount = amount,
        isIncome = isIncome,
        transactionTimeStamp = transactionTimeStamp,
        entryTimeStamp = entryTimeStamp
    )
}

