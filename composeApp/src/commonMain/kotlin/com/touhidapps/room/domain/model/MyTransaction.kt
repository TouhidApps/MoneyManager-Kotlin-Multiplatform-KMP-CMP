package com.touhidapps.room.domain.model


data class MyTransaction(
    val id: Int = 0,
    val title: String = "",
    val amount: Double = 0.0,
    val isIncome: Boolean = false,
    val transactionTimeStamp: Long = 0, // Millis
    val entryTimeStamp: String = ""
)


