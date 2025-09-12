package com.touhidapps.room.presentation.home

import com.touhidapps.room.domain.model.Transaction


sealed interface HomeEvents {
    data object LoadAllTransactions : HomeEvents
    data object SaveTransaction : HomeEvents
    data class EditTransaction(val transaction: Transaction?) : HomeEvents
    data object CancelUpdate : HomeEvents
    data class ChangeTitle(val title: String) : HomeEvents
    data class ChangeAmount(val amount: String) : HomeEvents
    data class ChangeIsIncome(val isIncome: Boolean) : HomeEvents
    data class ChangeDate(val date: Long) : HomeEvents
    data class ItemDeleteYes(val transaction: Transaction?) : HomeEvents

}


