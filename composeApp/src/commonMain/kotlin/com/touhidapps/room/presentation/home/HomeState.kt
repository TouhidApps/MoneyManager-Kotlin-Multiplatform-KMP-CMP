package com.touhidapps.room.presentation.home

import com.touhidapps.room.domain.model.Summary
import com.touhidapps.room.domain.model.Transaction
import com.touhidapps.room.utils.endOfTodayMillis

data class HomeState(
    val isLoading: Boolean = false,
    val transactions: List<Transaction> = emptyList(),
    val summary: Summary? = null,

    val title: String = "",
    val amount: String = "",
    val isIncome: Boolean = false,
    val selectedDate: Long = endOfTodayMillis(),
    val itemIdForUpdate: Int? = null,
)
