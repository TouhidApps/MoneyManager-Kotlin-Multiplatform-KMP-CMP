package com.touhidapps.room.presentation.home

import androidx.lifecycle.viewModelScope
import com.touhidapps.room.base.BaseViewModel
import com.touhidapps.room.domain.model.Transaction
import com.touhidapps.room.domain.usecase.TransactionDeleteUseCase
import com.touhidapps.room.domain.usecase.TransactionGetAllUseCase
import com.touhidapps.room.domain.usecase.TransactionUpsertUseCase
import com.touhidapps.room.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val transactionUpsertUseCase: TransactionUpsertUseCase,
    private val transactionGetAllUseCase: TransactionGetAllUseCase,
    private val transactionDeleteUseCase: TransactionDeleteUseCase,
): BaseViewModel(), HomeContract {

    private val _homeState = MutableStateFlow(HomeState())
    override val homeState: StateFlow<HomeState> = _homeState.asStateFlow()

    private val _actions = MutableSharedFlow<HomeActions>(replay = 0, extraBufferCapacity = 1)
    override val actions: Flow<HomeActions> = _actions

    init {
        onEvent(HomeEvents.LoadAllTransactions)
    }

    override fun onEvent(event: HomeEvents) {

        when(event) {

            is HomeEvents.EditTransaction -> {

                _homeState.update {
                    it.copy(
                        title = event.transaction?.title ?: "",
                        amount = "${event.transaction?.amount ?: 0.0}",
                        itemIdForUpdate = event.transaction?.id,
                        isIncome = event.transaction?.isIncome ?: false,
                        selectedDate = event.transaction?.transactionTimeStamp ?: getCurrentTimeMillis(),
                    )
                }

            }
            HomeEvents.LoadAllTransactions -> {

                getAllTransactions()

            }
            HomeEvents.SaveTransaction -> {

                saveTransaction()

            }

            HomeEvents.CancelUpdate -> {

                _homeState.update {
                    it.copy(
                        title = "",
                        amount = "",
                        itemIdForUpdate = null,
                        isIncome = false,
                        selectedDate = getCurrentTimeMillis(),
                    )
                }

            }

            is HomeEvents.ChangeTitle -> {
                _homeState.update { it.copy(title = event.title) }
            }

            is HomeEvents.ChangeAmount -> {
                _homeState.update { it.copy(amount = "${event.amount}") }
            }

            is HomeEvents.ChangeIsIncome -> {
                _homeState.update { it.copy(isIncome = event.isIncome) }
            }

            is HomeEvents.ChangeDate -> {
                _homeState.update { it.copy(selectedDate = event.date) }
            }

            is HomeEvents.ItemDeleteYes -> {

                deleteTransaction(event.transaction)

            }


        }

    }

    private fun saveTransaction() {

        viewModelScope.launch {

            when {
                _homeState.value.title?.isEmpty() == true -> {
                    _actions.emit(HomeActions.ShowSnackbar("⚠️ Title required"))
                    return@launch
                }

                _homeState.value.amount?.isEmpty() == true -> {
                    _actions.emit(HomeActions.ShowSnackbar("⚠️ Amount required"))
                    return@launch
                }
            }

            val transaction = Transaction(
                id = _homeState.value.itemIdForUpdate ?: 0, // 0 means insert, Room will autogenerate
                title = _homeState.value.title ?: "",
                amount = _homeState.value.amount?.toDouble() ?: 0.0,
                isIncome = _homeState.value.isIncome,
                transactionTimeStamp = _homeState.value.selectedDate ?: getCurrentTimeMillis(),
                entryTimeStamp = formatMillisWithTime(_homeState.value.selectedDate ?: getCurrentTimeMillis())
            )

            _homeState.update { it.copy(isLoading = true) }
            val ok = transactionUpsertUseCase(transaction) // suspend Boolean
            _homeState.update { it.copy(isLoading = false) }
            _actions.emit(HomeActions.ShowSnackbar(if (ok) "Saved ✅" else "Failed ❌"))
            if (_homeState.value.itemIdForUpdate != null) { // when update data
                _homeState.update { it.copy(selectedDate = getCurrentTimeMillis()) }
            }
            if (ok) onEvent(HomeEvents.LoadAllTransactions) // Refresh data

        }
    }

    private fun getAllTransactions() {

        viewModelScope.launch {

            if (_homeState.value.isLoading) return@launch

            _homeState.update { it.copy(isLoading = true, title = "", amount = "", itemIdForUpdate = null) }
            val res = transactionGetAllUseCase() // suspend Boolean
            _homeState.update { it.copy(isLoading = false, transactions = res) }

        }

    }


    private fun deleteTransaction(transaction: Transaction?) {

        viewModelScope.launch {


            transaction?.let {
                _homeState.update { it.copy(isLoading = true) }
                val res = transactionDeleteUseCase(it)
                _homeState.update { it.copy(isLoading = false) }
                _actions.emit(HomeActions.ShowSnackbar(if(res) "Transaction deleted successfully" else "⚠️ Something went wrong"))

                onEvent(HomeEvents.LoadAllTransactions) // Refresh data


//                    .onStart {
//                        _homeState.update { it.copy(isLoading = true) }
//                    }
//                    .catch { cause ->
//                        _homeState.update { it.copy(isLoading = false) }
//                        _actions.emit(HomeActions.ShowSnackbar("⚠️ Something went wrong (Exception: ${cause.message})"))
//                    }
//                    .collect { result ->
//                        _homeState.update { it.copy(isLoading = false) }
//                        _actions.emit(HomeActions.ShowSnackbar(if(result) "Transaction deleted successfully" else "⚠️ Something went wrong"))
//
//                        onEvent(HomeEvents.LoadAllTransactions) // Refresh data
//                    }
            }


        }
    }

}