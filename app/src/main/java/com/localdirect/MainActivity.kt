package com.localdirect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.localdirect.core.safetyLaunch
import com.localdirect.ui.LocalDirectTheme
import com.localdirect.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import java.net.ServerSocket
import java.net.Socket

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val vm: MainViewModel by viewModels()
    private val receiveSocket = ServerSocket(8888)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val ipAddresses by vm.ipAddresses.collectAsState()
            LocalDirectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyColumn(modifier = Modifier.padding(innerPadding)) {

                    }
                }
            }
        }

        sendData()
        receiveData()
    }

    private fun sendData() {
        CoroutineScope(Dispatchers.IO).safetyLaunch {
            val socket = Socket("192.168.0.100", 9999)
            socket.getOutputStream().write(LOCALDIRECT_HANDSHAKE.toByteArray(Charsets.US_ASCII))

            val input = socket.getInputStream()
            val data = input.readBytes().toString(Charsets.US_ASCII)

            Timber.e(data)
        }
    }

    private fun receiveData() {
        CoroutineScope(Dispatchers.IO).safetyLaunch {
            val socket = receiveSocket.accept()
            val input = socket.getInputStream()
            input.use {
                val dataArray = it.readBytes()
                Timber.e(dataArray.toString(Charsets.US_ASCII))
            }
        }
    }
}

const val LOCALDIRECT_HANDSHAKE = "com.localdirect.handshake"