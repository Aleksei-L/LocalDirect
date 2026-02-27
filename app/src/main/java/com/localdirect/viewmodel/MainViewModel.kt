package com.localdirect.viewmodel

import androidx.lifecycle.ViewModel
import com.localdirect.core.NetworkStateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkStateRepository: NetworkStateRepository,
    private val coroutineScope: CoroutineScope
) : ViewModel() {
    //todo exclude to shared module
    //private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val mIpAddresses = MutableStateFlow("")
    val ipAddresses = mIpAddresses.asStateFlow()

    init {
        handleNetworkState()
    }

    fun handleNetworkState() = coroutineScope.launch {
        networkStateRepository.networkStateFlow.collect { networkState ->
            val allAddresses = networkState.linkAddresses
            for (address in allAddresses) {
                val ip = address.address.toString()
                if (!ip.contains(':'))
                    mIpAddresses.emit(ip)
            }
        }
    }
}