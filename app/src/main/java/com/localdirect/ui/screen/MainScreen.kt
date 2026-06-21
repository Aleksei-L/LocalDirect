package com.localdirect.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onSettingsButtonClick: () -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("This is main")
            Button(onClick = onSettingsButtonClick) {
                Text("Click")
            }
        }
    }
}