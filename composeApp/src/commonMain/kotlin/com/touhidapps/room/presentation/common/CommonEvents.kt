package com.touhidapps.room.presentation.common


sealed interface CommonEvents {
//    data object LoadAllTransactions : SharedEvents

    // No need for now

    data class ShowSnackbar(val message: String) : CommonEvents


}


