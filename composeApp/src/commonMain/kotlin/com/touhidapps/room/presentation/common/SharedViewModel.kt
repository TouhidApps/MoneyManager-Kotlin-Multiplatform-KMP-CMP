package com.touhidapps.room.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SharedViewModel: ViewModel(), SharedContract {
    private val _snackbarMessage = MutableSharedFlow<String>()
    override val snackbarMessage: SharedFlow<String> = _snackbarMessage
    override fun showSnackbar(message: String) {
        viewModelScope.launch { _snackbarMessage.emit(message) }
    }
}
