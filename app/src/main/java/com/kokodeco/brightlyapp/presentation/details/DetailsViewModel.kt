package com.kokodeco.brightlyapp.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kokodeco.brightlyapp.domain.model.Article
import com.kokodeco.brightlyapp.domain.usecases.news.DeleteArticle
import com.kokodeco.brightlyapp.domain.usecases.news.GetSavedArticle
import com.kokodeco.brightlyapp.domain.usecases.news.UpsertArticle
import com.kokodeco.brightlyapp.util.UIComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getSavedArticleUseCase: GetSavedArticle,
    private val deleteArticleUseCase: DeleteArticle,
    private val upsertArticleUseCase: UpsertArticle
) : ViewModel() {

    var sideEffect by mutableStateOf<UIComponent?>(null)
        private set

    // State to track if the article is bookmarked
    var isArticleBookmarked by mutableStateOf(false)
        private set

    fun checkBookmarkStatus(articleUrl: String) {
        viewModelScope.launch {
            val article = getSavedArticleUseCase(url = articleUrl)
            isArticleBookmarked = article != null
        }
    }

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    handleArticleBookmarking(event.article)
                }
            }
            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }

    private suspend fun handleArticleBookmarking(article: Article) {
        val existingArticle = getSavedArticleUseCase(url = article.url)
        if (existingArticle == null) {
            upsertArticle(article)
        } else {
            deleteArticle(article)
        }
    }

    private suspend fun deleteArticle(article: Article) {
        deleteArticleUseCase(article = article)
        sideEffect = UIComponent.Toast("Article deleted")
        isArticleBookmarked = false // Update the bookmark state to reflect removal
    }

    private suspend fun upsertArticle(article: Article) {
        upsertArticleUseCase(article = article) { result ->
            sideEffect = result
        }
        isArticleBookmarked = true // Update the bookmark state to reflect addition
    }
}

