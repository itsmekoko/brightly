package com.kokodeco.brightlyapp.presentation.details

import com.kokodeco.brightlyapp.domain.model.Article

sealed class DetailsEvent {

    data class UpsertDeleteArticle(val article: Article) : DetailsEvent()

    object RemoveSideEffect : DetailsEvent()
}
