package com.localdirect.viewmodel

import androidx.lifecycle.ViewModel
import com.localdirect.core.UiStateRepository
import com.localdirect.core.network.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    networkManager: NetworkManager
) : ViewModel() {
    val uiStateFlow = UiStateRepository.uiStateFlow
    val serverIp = networkManager.serverIp
}