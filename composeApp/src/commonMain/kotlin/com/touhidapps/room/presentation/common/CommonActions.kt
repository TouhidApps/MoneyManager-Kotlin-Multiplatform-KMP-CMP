package com.touhidapps.room.presentation.common


sealed interface CommonActions { // SharedEffects

    data class ShowSnackbar(val message: String) : CommonActions

}

