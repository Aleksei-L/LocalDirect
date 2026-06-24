package com.localdirect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.localdirect.ui.LocalDirectTheme
import com.localdirect.ui.navigation.Main
import com.localdirect.ui.navigation.Settings
import com.localdirect.ui.screen.MainScreen
import com.localdirect.ui.screen.SettingsScreen
import com.localdirect.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocalDirectTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Main
                ) {
                    composable<Main> {
                        MainScreen(
                            uiStateFlow = vm.uiStateFlow,
                            serverIpFlow = vm.serverIp,
                            onSettingsButtonClick = {
                                navController.navigate(Settings)
                            }
                        )
                    }
                    composable<Settings> {
                        SettingsScreen(onBackButtonClick = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}