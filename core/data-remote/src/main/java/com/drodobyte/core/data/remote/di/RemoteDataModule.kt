package com.drodobyte.core.data.remote.di

import android.content.Context
import com.drodobyte.core.data.remote.Factory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal class RemoteDataModule {

    @Provides
    @ViewModelScoped
    fun petDataSource(@ApplicationContext context: Context) =
        Factory.petDataSource(context)

    @Provides
    @ViewModelScoped
    fun imageDataSource(@ApplicationContext context: Context) =
        Factory.imageDataSource(context)
}
