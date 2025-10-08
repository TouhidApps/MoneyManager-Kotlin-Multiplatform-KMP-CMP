package com.touhidapps.room.data.mapper

import com.touhidapps.room.data.datasource.local.db.entity.SummaryEntity
import com.touhidapps.room.data.datasource.local.db.entity.TransactionEntity
import com.touhidapps.room.domain.model.Summary
import com.touhidapps.room.domain.model.Transaction


fun SummaryEntity.toDomain(): Summary = Summary(
    totalIncome = totalIncome,
    totalExpense = totalExpense,
    balance = balance
)

//Use Entity for DB, Dto for network. for postfix and also for mapping toDto/toEntity
fun Summary.toEntity(): SummaryEntity {
    return SummaryEntity(
        totalIncome = totalIncome,
        totalExpense = totalExpense,
        balance = balance
    )
}

