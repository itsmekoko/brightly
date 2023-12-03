package com.kokodeco.brightlyapp.domain.usecases.news

import com.kokodeco.brightlyapp.data.local.NewsDao
import com.kokodeco.brightlyapp.domain.model.Article
import javax.inject.Inject

class GetSavedArticle @Inject constructor(
    private val newsDao: NewsDao
) {

    suspend operator fun invoke(url: String): Article? {
        return newsDao.getArticle(url = url)
    }
}
