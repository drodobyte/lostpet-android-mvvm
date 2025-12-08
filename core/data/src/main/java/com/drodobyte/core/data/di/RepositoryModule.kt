package com.drodobyte.core.data.di

import com.drodobyte.core.data.local.ImageLocalDataSource
import com.drodobyte.core.data.remote.ImageRemoteDataSource
import com.drodobyte.core.data.remote.PetRemoteDataSource
import com.drodobyte.core.data.repository.DefaultPetRepository
import com.drodobyte.core.data.repository.PetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RepositoryModule {

    @Singleton
    @Provides
    fun petRepository(
        localImage: ImageLocalDataSource,
        remoteImage: ImageRemoteDataSource,
        remotePet: PetRemoteDataSource,
    ): PetRepository =
        DefaultPetRepository(remotePet, localImage, remoteImage)
}
