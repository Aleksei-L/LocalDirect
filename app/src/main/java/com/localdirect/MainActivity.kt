package com.localdirect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.localdirect.core.safetyLaunch
import com.localdirect.ui.LocalDirectTheme
import com.localdirect.ui.navigation.Main
import com.localdirect.ui.navigation.Settings
import com.localdirect.ui.screen.MainScreen
import com.localdirect.ui.screen.SettingsScreen
import com.localdirect.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import java.net.Socket

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
                            uiStateFlow = vm.uiStateRepository.uiStateFlow,
                            onSettingsButtonClick = {
                                navController.navigate(Settings)
                            }
                        )
                    }
                    composable<Settings> {
                        SettingsScreen(onBackButtonClick = {
                            navController.navigate(Main)
                        })
                    }
                }
            }
        }

        sendData()
    }

    private fun sendData() {
        CoroutineScope(Dispatchers.IO).safetyLaunch {
            val socket = Socket("192.168.0.100", 3316)
            socket.getOutputStream().write(LOCALDIRECT_HANDSHAKE.toByteArray(Charsets.US_ASCII))

            val input = socket.getInputStream()
            val data = input.readBytes().toString(Charsets.US_ASCII)

            Timber.e(data)
        }
    }
}

const val LOCALDIRECT_HANDSHAKE = "com.localdirect.handshake"