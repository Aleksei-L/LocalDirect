package com.localdirect.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.localdirect.core.safetyLaunch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

class NetworkStateRepository @Inject constructor(
    appContext: Context,
    coroutineScope: CoroutineScope
) {
    private val mNetworkStateFlow = MutableStateFlow(LinkProperties())
    val networkStateFlow = mNetworkStateFlow.asStateFlow()

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Timber.i("Network available: $network")
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            Timber.i("Network capability changed: $network, $networkCapabilities")
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Timber.i("Network lost: $network")
        }

        override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
            super.onLinkPropertiesChanged(network, linkProperties)
            Timber.i("Network link properties changed: $network, $linkProperties")
            coroutineScope.safetyLaunch {
                mNetworkStateFlow.emit(linkProperties)
            }
        }
    }

    init {
        val connectivityManager =
            appContext.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}