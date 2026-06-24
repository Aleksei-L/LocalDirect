package com.localdirect.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

object UiStateRepository {
    private val mUiStateFlow = MutableStateFlow(UiState.IDLE)
    val uiStateFlow = mUiStateFlow.asStateFlow()

    fun emitUiState(uiState: UiState) {
        Timber.i("UI state update: oldState=${uiStateFlow.value}, newState=$uiState")
        mUiStateFlow.value = uiState
    }
}