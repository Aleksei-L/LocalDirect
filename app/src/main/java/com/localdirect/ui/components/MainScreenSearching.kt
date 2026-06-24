package com.localdirect.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.localdirect.ui.LocalDirectTheme

@Composable
fun MainScreenSearching(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Searching for server...")
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenSearchingPreview() {
    LocalDirectTheme {
        MainScreenSearching()
    }
}