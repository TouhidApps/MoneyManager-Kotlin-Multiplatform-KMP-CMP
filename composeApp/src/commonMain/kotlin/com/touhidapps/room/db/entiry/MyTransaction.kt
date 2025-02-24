package com.touhidapps.room.db.entiry

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MyTransaction")
data class MyTransaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    val amount: Double = 0.0,
    val isIncome: Boolean = false,
    val timeStamp: String = "",
)

