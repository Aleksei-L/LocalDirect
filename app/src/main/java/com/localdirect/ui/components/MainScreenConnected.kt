package com.localdirect.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.localdirect.core.data.IpAddress
import com.localdirect.ui.LocalDirectTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreenConnected(
    modifier: Modifier = Modifier,
    serverIpFlow: StateFlow<IpAddress>
) {
    val ipAddress by serverIpFlow.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Connected with $ipAddress")
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenConnectedPreview() {
    LocalDirectTheme {
        MainScreenConnected(serverIpFlow = MutableStateFlow(IpAddress("192.168.0.1/24")))
    }
}