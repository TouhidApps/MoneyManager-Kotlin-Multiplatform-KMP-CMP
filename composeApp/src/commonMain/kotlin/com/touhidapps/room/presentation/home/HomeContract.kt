package com.touhidapps.room.presentation.home

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface HomeContract {
    val state: StateFlow<HomeState>
    fun onEvent(event: HomeEvents)
    val actions: Flow<HomeActions>
}


