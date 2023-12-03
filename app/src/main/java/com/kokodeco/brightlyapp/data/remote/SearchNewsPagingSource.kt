package com.kokodeco.brightlyapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kokodeco.brightlyapp.domain.model.Article

class SearchNewsPagingSource(
    private val newsApi: BrightlyApi,
    private val searchQuery: String,
    private val sources: String
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition.let { anchorPosition ->
            val anchorPage = anchorPosition?.let { state.closestPageToPosition(it) }
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private var totalNewsCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val newsResponse = newsApi.searchNews(
                searchQuery = searchQuery,
                sources = sources,
                page = page
            )
            totalNewsCount += newsResponse.articles.size
            // solving a problem of the api where it gives duplicate articles
            val articles = newsResponse.articles.distinctBy { it.title }
            LoadResult.Page(
                data = articles,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }
}
