package com.localdirect.core.network

import com.localdirect.core.UiState
import com.localdirect.core.UiStateRepository
import com.localdirect.core.data.IpAddress
import com.localdirect.core.safetyLaunch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import java.net.InetAddress
import java.net.Socket
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class ConnectionRepository @Inject constructor(
    private val coroutineScope: CoroutineScope
) {
    private val mServerIp = MutableStateFlow(IpAddress.Init)
    val serverIp = mServerIp.asStateFlow()

    lateinit var socket: Socket

    fun handleConnectionWithServer(ipAddressesFlow: StateFlow<IpAddress>) {
        coroutineScope.safetyLaunch {
            readFromSocket()
        }
        coroutineScope.safetyLaunch {
            ipAddressesFlow.collect {
                if (it == IpAddress.Init) {
                    return@collect
                } else {
                    UiStateRepository.emitUiState(UiState.SEARCHING)
                }
                val localAddresses = it.getLocalAddresses()
                for (address in localAddresses) {
                    coroutineScope.safetyLaunch(logging = false) {
                        sendHandshake(address)
                    }
                }
            }
        }
    }

    private fun sendHandshake(ipAddress: IpAddress) {
        socket = Socket(ipAddress.stringIpAddress, LOCALDIRECT_PORT)
        Timber.i("Opened connection with $ipAddress")

        socket.getOutputStream().write(
            LOCALDIRECT_HANDSHAKE.toByteArray(Charsets.US_ASCII),
            0,
            LOCALDIRECT_HANDSHAKE.length
        )
    }

    private suspend fun readFromSocket() {
        while (true) {
            if (::socket.isInitialized) {
                val input = socket.getInputStream()
                val data = ByteArray(LOCALDIRECT_ACCEPT.length)

                input.read(
                    data,
                    0,
                    LOCALDIRECT_ACCEPT.length
                )

                val receivedData = data.toString(Charsets.US_ASCII)
                if (receivedData == LOCALDIRECT_ACCEPT) {
                    Timber.i("Received ACCEPT from server ${socket.inetAddress.toIp()}")
                    coroutineScope.safetyLaunch {
                        establishConnection()
                    }
                }
            } else {
                delay(1.seconds)
            }
        }
    }

    private fun establishConnection() {
        socket.getOutputStream().write(
            LOCALDIRECT_ESTABLISH.toByteArray(Charsets.US_ASCII),
            0,
            LOCALDIRECT_ESTABLISH.length
        )
        mServerIp.value = IpAddress(
            "${socket.inetAddress.toIp()}/0" //todo
        )
        UiStateRepository.emitUiState(UiState.CONNECTED)


        Timber.i("Connection with server ${socket.inetAddress.toIp()} was established")
    }

    private fun InetAddress.toIp() = this.toString().substringAfter('/')
}