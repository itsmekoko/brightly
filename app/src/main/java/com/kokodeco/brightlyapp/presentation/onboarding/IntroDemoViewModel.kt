package com.kokodeco.brightlyapp.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kokodeco.brightlyapp.domain.usecases.appEntry.SaveAppEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class IntroDemoViewModel @Inject constructor(
    private val saveAppEntry: SaveAppEntry
) : ViewModel() {

    fun onEvent(event: IntroDemoEvent) {
        when (event) {
            is IntroDemoEvent.SaveAppEntry -> {
                saveUserEntry()
            }
        }
    }

    private fun saveUserEntry() {
        viewModelScope.launch {
            saveAppEntry()
        }
    }
}
