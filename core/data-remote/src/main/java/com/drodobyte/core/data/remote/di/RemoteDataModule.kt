package com.drodobyte.core.data.remote.di

import android.content.Context
import com.drodobyte.core.data.remote.Factory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RemoteDataModule {

    @Provides
    @Singleton
    fun petDataSource(@ApplicationContext context: Context) =
        Factory.petDataSource(context)

    @Provides
    @Singleton
    fun imageDataSource(@ApplicationContext context: Context) =
        Factory.imageDataSource(context)
}
