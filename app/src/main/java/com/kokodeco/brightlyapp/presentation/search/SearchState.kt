package com.kokodeco.brightlyapp.presentation.search

import androidx.paging.PagingData
import com.kokodeco.brightlyapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null
)
