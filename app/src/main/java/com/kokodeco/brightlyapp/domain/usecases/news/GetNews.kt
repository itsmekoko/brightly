package com.kokodeco.brightlyapp.domain.usecases.news

import androidx.paging.PagingData
import com.kokodeco.brightlyapp.domain.model.Article
import com.kokodeco.brightlyapp.domain.repository.BrightlyRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetNews @Inject constructor(
    private val brightlyRepository: BrightlyRepository
) {

    operator fun invoke(sources: List<String>): Flow<PagingData<Article>> {
        return brightlyRepository.getNews(sources = sources)
    }
}
