package com.drodobyte.domain.usecase.di

import com.drodobyte.domain.usecase.RecommendedIntakeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class UseCaseModule {

    @Provides
    @Singleton
    fun useCase() = RecommendedIntakeUseCase()
}
