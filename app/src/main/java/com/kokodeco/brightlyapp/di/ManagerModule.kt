package com.kokodeco.brightlyapp.di

import com.kokodeco.brightlyapp.data.manager.LocalUserManagerImpl
import com.kokodeco.brightlyapp.domain.manager.LocalUserManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    @Singleton
    abstract fun bindLocalUserManger(localUserMangerImpl: LocalUserManagerImpl): LocalUserManager
}
