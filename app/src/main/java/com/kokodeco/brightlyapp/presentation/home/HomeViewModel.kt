package com.kokodeco.brightlyapp.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kokodeco.brightlyapp.domain.model.Article
import com.kokodeco.brightlyapp.domain.usecases.news.GetNews
import com.kokodeco.brightlyapp.util.NetworkConnectivityChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNews,
    private val networkConnectivityChecker: NetworkConnectivityChecker
) : ViewModel() {

    var state = mutableStateOf(HomeState())
        private set

    private val _news = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val news: StateFlow<PagingData<Article>> = _news

    private val _isNetworkAvailable = MutableStateFlow(true)
    val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable

    init {
        checkNetworkAvailability()
        reloadNews()
    }

    private fun checkNetworkAvailability() {
        _isNetworkAvailable.value = networkConnectivityChecker.hasNetworkAccess()
    }

    fun reloadNews() {
        checkNetworkAvailability()
        if (_isNetworkAvailable.value) {
            viewModelScope.launch {
                getNewsUseCase(sources = listOf("bbc-news", "abc-news"))
                    .cachedIn(viewModelScope)
                    .collectLatest { pagingData ->
                        _news.value = pagingData
                    }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.UpdateScrollValue -> updateScrollValue(event.newValue)
            is HomeEvent.UpdateMaxScrollingValue -> updateMaxScrollingValue(event.newValue)
        }
    }

    private fun updateScrollValue(newValue: Int) {
        state.value = state.value.copy(scrollValue = newValue)
    }

    private fun updateMaxScrollingValue(newValue: Int) {
        state.value = state.value.copy(maxScrollingValue = newValue)
    }
}
