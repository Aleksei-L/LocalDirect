package com.localdirect.core.network

import com.localdirect.core.UiState
import com.localdirect.core.UiStateRepository
import com.localdirect.core.data.IpAddress
import com.localdirect.core.safetyLaunch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import timber.log.Timber
import java.io.OutputStream
import java.net.InetAddress
import java.net.Socket
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class ConnectionRepository @Inject constructor(
    private val coroutineScope: CoroutineScope
) {
    private val mServerIp = MutableStateFlow(IpAddress.Init)
    val serverIp = mServerIp.asStateFlow()

    private val buffer = ByteArray(256)

    lateinit var socket: Socket
    lateinit var outputStream: OutputStream

    var readingJob: Job? = null

    fun handleConnectionWithServer(ipAddressesFlow: StateFlow<IpAddress>) {
        readingJob = coroutineScope.safetyLaunch {
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
        outputStream = socket.getOutputStream()
        Timber.i("Opened connection with $ipAddress")

        outputStream.write(
            ClientConst.LOCALDIRECT_HANDSHAKE.toByteArray(Charsets.US_ASCII),
            0,
            ClientConst.LOCALDIRECT_HANDSHAKE.length
        )
    }

    private suspend fun readFromSocket() {
        while (currentCoroutineContext().isActive) {
            if (::socket.isInitialized) {
                val input = socket.getInputStream()
                input.read(buffer)
                val receivedData = handleSocketMessage(buffer)
                buffer.fill(0)

                if (receivedData == ServerConsts.LOCALDIRECT_ACCEPT) {
                    Timber.i("Received ACCEPT from server ${socket.inetAddress.toIp()}")
                    coroutineScope.safetyLaunch {
                        establishConnection()
                    }
                } else if (receivedData == ServerConsts.LOCALDIRECT_TERMINAL) {
                    Timber.e("Server terminaled")
                    UiStateRepository.emitUiState(UiState.IDLE)
                    readingJob?.cancel()
                    socket.close()
                }
            } else {
                delay(1.seconds)
            }
        }
    }

    private fun handleSocketMessage(buffer: ByteArray): String =
        buffer.sliceArray(0..<(buffer.indexOfFirst { it == 0.toByte() }))
            .toString(Charsets.US_ASCII)

    private fun establishConnection() {
        outputStream.write(
            ClientConst.LOCALDIRECT_ESTABLISH.toByteArray(Charsets.US_ASCII),
            0,
            ClientConst.LOCALDIRECT_ESTABLISH.length
        )
        mServerIp.value = IpAddress(
            "${socket.inetAddress.toIp()}/0" //todo
        )
        UiStateRepository.emitUiState(UiState.CONNECTED)


        Timber.i("Connection with server ${socket.inetAddress.toIp()} was established")
    }

    private fun InetAddress.toIp() = this.toString().substringAfter('/')
}