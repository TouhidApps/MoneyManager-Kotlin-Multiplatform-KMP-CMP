package com.touhidapps.room.presentation.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CommonViewModel: ViewModel(), CommonContract {

    private val _state = MutableStateFlow(CommonState())
    override val state: StateFlow<CommonState> = _state.asStateFlow()

    private val _actions = MutableSharedFlow<CommonActions>(replay = 0, extraBufferCapacity = 1)
    override val actions: Flow<CommonActions> = _actions

    init {
//        onEvent(SharedEvents.Something)
    }

    override fun onEvent(event: CommonEvents) {
        when (event) {
            is CommonEvents.ShowSnackbar -> {
                _actions.tryEmit(CommonActions.ShowSnackbar(event.message))
            }
        }
    }


}
