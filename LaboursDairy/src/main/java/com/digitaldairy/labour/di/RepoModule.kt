package com.digitaldairy.labour.di

import com.digitaldairy.labour.repo.ILabourRepository
import com.digitaldairy.labour.repo.LabourRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun provideLabourRepository(impl: LabourRepository): ILabourRepository
}