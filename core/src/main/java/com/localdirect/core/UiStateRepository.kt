package com.localdirect.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UiStateRepository {
    private val mUiStateFlow = MutableStateFlow(UiState.IDLE)
    val uiStateFlow = mUiStateFlow.asStateFlow()
}