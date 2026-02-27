package com.localdirect.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class CoroutineScopeModule {
    @Singleton
    @Provides
    fun provideCoroutineScope(): CoroutineScope =
        CoroutineScope(Job() + Dispatchers.IO)
}