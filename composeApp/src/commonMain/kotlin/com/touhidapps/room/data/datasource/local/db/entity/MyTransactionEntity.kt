package com.touhidapps.room.data.datasource.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MyTransaction")
data class MyTransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    val amount: Double = 0.0,
    val isIncome: Boolean = false,
    val transactionTimeStamp: Long = 0, // Millis
    val entryTimeStamp: String = "", // Human readable timestamp when entering data
)

