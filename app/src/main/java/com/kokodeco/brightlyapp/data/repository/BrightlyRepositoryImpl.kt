package com.kokodeco.brightlyapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kokodeco.brightlyapp.data.local.NewsDao
import com.kokodeco.brightlyapp.data.remote.BrightlyApi
import com.kokodeco.brightlyapp.data.remote.NewsPagingSource
import com.kokodeco.brightlyapp.data.remote.SearchNewsPagingSource
import com.kokodeco.brightlyapp.domain.model.Article
import com.kokodeco.brightlyapp.domain.repository.BrightlyRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class BrightlyRepositoryImpl @Inject constructor(
    private val brightlyApi: BrightlyApi,
    private val newsDao: NewsDao

) : BrightlyRepository {
    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = brightlyApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    searchQuery = searchQuery,
                    newsApi = brightlyApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override suspend fun upsertArticle(article: Article) {
        newsDao.upsert(article)
    }

    override suspend fun deleteArticle(article: Article) {
        newsDao.delete(article)
    }

    override fun getArticles(): Flow<List<Article>> {
        return newsDao.getArticles()
    }

    override suspend fun getArticle(url: String): Article? {
        return newsDao.getArticle(url = url)
    }
}
