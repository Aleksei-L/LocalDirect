package com.localdirect.core.di

import android.content.Context
import com.localdirect.core.network.ConnectionRepository
import com.localdirect.core.network.NetworkManager
import com.localdirect.core.network.NetworkStateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class CoreModule {
    @Provides
    @Singleton
    fun provideNetworkStateRepository(
        @ApplicationContext appContext: Context,
        coroutineScope: CoroutineScope
    ): NetworkStateRepository = NetworkStateRepository(
        appContext,
        coroutineScope
    )

    @Provides
    @Singleton
    fun provideConnectionRepository(
        coroutineScope: CoroutineScope
    ): ConnectionRepository = ConnectionRepository(
        coroutineScope
    )

    @Provides
    @Singleton
    fun provideNetworkManager(
        coroutineScope: CoroutineScope,
        networkStateRepository: NetworkStateRepository,
        connectionRepository: ConnectionRepository
    ): NetworkManager = NetworkManager(
        coroutineScope,
        networkStateRepository,
        connectionRepository
    )
}