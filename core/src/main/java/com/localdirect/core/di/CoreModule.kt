package com.localdirect.core.di

import com.localdirect.core.UiStateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class CoreModule {
    @Provides
    @Singleton
    fun provideUiStateRepository(): UiStateRepository = UiStateRepository()
}