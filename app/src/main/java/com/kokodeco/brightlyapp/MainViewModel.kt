package com.kokodeco.brightlyapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kokodeco.brightlyapp.domain.usecases.appEntry.ReadAppEntry
import com.kokodeco.brightlyapp.presentation.navgraph.NavScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class MainViewModel @Inject constructor(
    readAppEntry: ReadAppEntry
) : ViewModel() {

    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(NavScreen.AppStartNavigation.navScreen)
    val startDestination: State<String> = _startDestination

    init {
        readAppEntry().onEach { shouldStartFromHomeScreen ->
            if (shouldStartFromHomeScreen) {
                _startDestination.value = NavScreen.NewsNavigation.navScreen
            } else {
                _startDestination.value = NavScreen.AppStartNavigation.navScreen
            }
            delay(200) // Without this delay, the onBoarding screen will show for a momentum.
            _splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}
