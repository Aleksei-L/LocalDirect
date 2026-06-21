package com.localdirect.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localdirect.core.NetworkStateRepository
import com.localdirect.core.data.IpAddress
import com.localdirect.core.safetyLaunch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkStateRepository: NetworkStateRepository
) : ViewModel() {
    private val mIpAddresses = MutableStateFlow(IpAddress("0.0.0.0/0"))
    val ipAddresses = mIpAddresses.asStateFlow()

    init {
        handleNetworkState()
    }

    private fun handleNetworkState() = viewModelScope.safetyLaunch {
        networkStateRepository.networkStateFlow.collect { networkState ->
            val allAddresses = networkState.linkAddresses
            for (address in allAddresses) {
                val ip = address.address.toString()
                if (!ip.contains(':'))
                    mIpAddresses.emit(
                        IpAddress(
                            "${ip.substringAfter('/')}/${address.prefixLength}"
                        )
                    )
            }
        }
    }
}