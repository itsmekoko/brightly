package com.kokodeco.brightlyapp.domain.usecases.appEntry

import com.kokodeco.brightlyapp.domain.manager.LocalUserManager
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ReadAppEntry @Inject constructor(
    private val localUserManager: LocalUserManager
) {

    operator fun invoke(): Flow<Boolean> {
        return localUserManager.readAppEntry()
    }
}
