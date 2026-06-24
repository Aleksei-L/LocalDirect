package com.localdirect.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.localdirect.ui.LocalDirectTheme
import com.localdirect.ui.components.SettingsAppBar

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { SettingsAppBar(onBackButtonClick = onBackButtonClick) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(text = "This is settings")
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    LocalDirectTheme {
        SettingsScreen { }
    }
}