package com.localdirect.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.localdirect.core.UiState
import com.localdirect.core.data.IpAddress
import com.localdirect.ui.components.MainAppBar
import com.localdirect.ui.components.MainScreenConnected
import com.localdirect.ui.components.MainScreenIdle
import com.localdirect.ui.components.MainScreenSearching
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    uiStateFlow: StateFlow<UiState>,
    serverIpFlow: StateFlow<IpAddress>,
    onSettingsButtonClick: () -> Unit,
) {
    val uiState by uiStateFlow.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { MainAppBar(onSettingsButtonClick = onSettingsButtonClick) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState) {
                UiState.IDLE -> {
                    MainScreenIdle(onSearchingServerButtonClick = {}) //todo
                }

                UiState.SEARCHING -> {
                    MainScreenSearching()
                }

                UiState.CONNECTED -> {
                    MainScreenConnected(serverIpFlow = serverIpFlow)
                }
            }
        }
    }
}