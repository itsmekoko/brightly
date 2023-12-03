package com.kokodeco.brightlyapp.domain.usecases.news

import com.kokodeco.brightlyapp.data.local.NewsDao
import com.kokodeco.brightlyapp.domain.model.Article
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSavedArticles @Inject constructor(
    private val newsDao: NewsDao
) {

    operator fun invoke(): Flow<List<Article>> {
        return newsDao.getArticles()
    }
}
