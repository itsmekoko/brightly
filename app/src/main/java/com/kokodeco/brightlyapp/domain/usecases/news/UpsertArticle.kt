package com.kokodeco.brightlyapp.domain.usecases.news

import com.kokodeco.brightlyapp.data.local.NewsDao
import com.kokodeco.brightlyapp.domain.model.Article
import com.kokodeco.brightlyapp.util.UIComponent
import javax.inject.Inject

class UpsertArticle @Inject constructor(
    private val newsDao: NewsDao
) {
    suspend operator fun invoke(article: Article, onResult: (UIComponent) -> Unit) {
        when {
            article.title.isEmpty() -> onResult(UIComponent.Toast("Article title is missing"))
            article.description.isEmpty() -> onResult(UIComponent.Toast("Article description is missing"))
            else -> {
                val articleToUpsert = if (article.author.isNullOrEmpty()) {
                    article.copy(author = "Unknown Author")
                } else {
                    article
                }

                newsDao.upsert(article = articleToUpsert)
                onResult(UIComponent.Toast("Article saved"))
            }
        }
    }
}