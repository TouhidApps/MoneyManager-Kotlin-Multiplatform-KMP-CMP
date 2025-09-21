package com.touhidapps.room.presentation.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CommonContract {

    val state: StateFlow<CommonState>
    fun onEvent(event: CommonEvents)
    val actions: Flow<CommonActions>

}
