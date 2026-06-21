package com.localdirect.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.localdirect.core.UiState
import com.localdirect.ui.components.LocalDirectAppBar
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    uiStateFlow: StateFlow<UiState>,
    onSettingsButtonClick: () -> Unit
) {
    val uiState = uiStateFlow.collectAsState()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { LocalDirectAppBar(onSettingsButtonClick = onSettingsButtonClick) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("This is main")
        }
    }
}