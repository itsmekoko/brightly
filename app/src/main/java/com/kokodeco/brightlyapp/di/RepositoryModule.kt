package com.kokodeco.brightlyapp.di

import com.kokodeco.brightlyapp.data.repository.BrightlyRepositoryImpl
import com.kokodeco.brightlyapp.domain.repository.BrightlyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(brightlyRepositoryImpl: BrightlyRepositoryImpl): BrightlyRepository
}
