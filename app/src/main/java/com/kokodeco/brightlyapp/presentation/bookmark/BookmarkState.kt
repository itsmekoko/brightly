package com.kokodeco.brightlyapp.presentation.bookmark

import com.kokodeco.brightlyapp.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()

)
