package com.touhidapps.room.presentation.home



sealed interface HomeActions { // HomeEffects
    data class ShowSnackbar(val message: String) : HomeActions

}