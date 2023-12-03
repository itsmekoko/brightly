package com.kokodeco.brightlyapp.data.remote.dto

import com.kokodeco.brightlyapp.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
