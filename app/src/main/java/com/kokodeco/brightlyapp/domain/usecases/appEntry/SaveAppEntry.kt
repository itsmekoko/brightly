package com.kokodeco.brightlyapp.domain.usecases.appEntry

import com.kokodeco.brightlyapp.domain.manager.LocalUserManager
import javax.inject.Inject

class SaveAppEntry @Inject constructor(
    private val localUserManager: LocalUserManager
) {

    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }
}
