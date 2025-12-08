package com.drodobyte.core.data.local.di

import android.content.Context
import com.drodobyte.core.data.local.Factory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class LocalDataModule {

    @Provides
    @Singleton
    fun localDataSource(@ApplicationContext context: Context) =
        Factory.dataSource(context)
}
