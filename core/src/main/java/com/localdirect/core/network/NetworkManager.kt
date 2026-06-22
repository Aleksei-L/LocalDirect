package com.localdirect.core.network

import com.localdirect.core.data.IpAddress
import com.localdirect.core.safetyLaunch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class NetworkManager @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val networkStateRepository: NetworkStateRepository,
    connectionRepository: ConnectionRepository
) {
    private val mIpAddresses = MutableStateFlow(IpAddress.Init)
    val serverIp = connectionRepository.serverIp

    init {
        handleNetworkState()
        connectionRepository.handleConnectionWithServer(mIpAddresses)
    }

    private fun handleNetworkState() = coroutineScope.safetyLaunch {
        networkStateRepository.networkStateFlow.collect { networkState ->
            val allAddresses = networkState.linkAddresses
            for (address in allAddresses) {
                val ip = address.address.toString()
                if (!ip.contains(':')) {
                    mIpAddresses.emit(
                        IpAddress(
                            "${ip.substringAfter('/')}/${address.prefixLength}"
                        )
                    )
                }
            }
        }
    }
}