package com.touhidapps.room.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.touhidapps.room.db.entiry.MyTransaction
import com.touhidapps.room.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TransactionViewModel(private val repository: TransactionRepository): ViewModel() {

    private val _upsert = MutableStateFlow<Boolean>(false)
    val upsert: StateFlow<Boolean> = _upsert.asStateFlow()

    private val _deleteAll = MutableStateFlow<Boolean>(false)
    val deleteAll: StateFlow<Boolean> = _deleteAll.asStateFlow()

    private val _delete = MutableStateFlow<Boolean>(false)
    val delete: StateFlow<Boolean> = _delete.asStateFlow()


    // Get all
    val transactions: StateFlow<List<MyTransaction>> = repository.getAll().stateIn(
        viewModelScope,
        SharingStarted.Lazily, // Flow won't start collecting immediately when the ViewModel is created. only starts when the UI subscribes to it.
        emptyList()
    )

    fun upsert(myTransaction: MyTransaction) {

        viewModelScope.launch {
            _upsert.value = repository.upsert(myTransaction)
        }

    }

    fun deleteAll() {

        viewModelScope.launch {
            repository.deleteAll()
        }

    }

    fun delete(myTransaction: MyTransaction) {

        viewModelScope.launch {
            repository.delete(myTransaction)
        }

    }


}








