package com.localdirect.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.localdirect.ui.LocalDirectTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit
) {
    LocalDirectTheme {
        LaunchedEffect(Unit) {
            delay(1.seconds)
            onBackButtonClick()
        }
        Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Text("This is settings")
            }
        }
    }
}