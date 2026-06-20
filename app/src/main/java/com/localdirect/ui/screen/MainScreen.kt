package com.localdirect.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.localdirect.ui.navigation.Settings

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("This is main")
            Button(onClick = {
                navController.navigate(Settings)
            }) {
                Text("Click")
            }
        }
    }
}