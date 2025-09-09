package com.touhidapps.room.presentation.home

import com.touhidapps.room.domain.model.MyTransaction
import com.touhidapps.room.utils.getCurrentTimeMillis

data class HomeState(
    val isLoading: Boolean = false,

    val transactions: List<MyTransaction> = emptyList(),
    val upsert: Boolean = false,
    val delete: Boolean = false,

    val title: String = "",
    val amount: String = "",
    val isIncome: Boolean = false,
    val selectedDate: Long = getCurrentTimeMillis(),
    val itemIdForUpdate: Int? = null,
    val showDataPicker: Boolean = false
)
