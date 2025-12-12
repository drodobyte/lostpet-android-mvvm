package com.drodobyte.core.data.di

import com.drodobyte.core.data.local.ImageLocalDataSource
import com.drodobyte.core.data.remote.ImageRemoteDataSource
import com.drodobyte.core.data.remote.PetRemoteDataSource
import com.drodobyte.core.data.repository.DefaultPetRepository
import com.drodobyte.core.data.repository.PetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal class RepositoryModule {

    @ViewModelScoped
    @Provides
    fun petRepository(
        localImage: ImageLocalDataSource,
        remoteImage: ImageRemoteDataSource,
        remotePet: PetRemoteDataSource,
    ): PetRepository =
        DefaultPetRepository(remotePet, localImage, remoteImage)
}
