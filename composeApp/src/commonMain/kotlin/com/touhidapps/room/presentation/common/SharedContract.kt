package com.touhidapps.room.presentation.common

import kotlinx.coroutines.flow.SharedFlow

interface SharedContract {

    val snackbarMessage: SharedFlow<String>
    fun showSnackbar(message: String)

}
