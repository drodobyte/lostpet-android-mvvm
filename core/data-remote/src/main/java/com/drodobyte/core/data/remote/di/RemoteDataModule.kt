package com.drodobyte.core.data.remote.di

import com.drodobyte.core.data.remote.Factory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RemoteDataModule {

    @Provides
    @Singleton
    fun petDataSource() = Factory.petDataSource()

    @Provides
    @Singleton
    fun imageDataSource() = Factory.imageDataSource()
}
