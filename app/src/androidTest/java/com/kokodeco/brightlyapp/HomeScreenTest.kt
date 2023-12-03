package com.kokodeco.brightlyapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kokodeco.brightlyapp.domain.model.Article
import com.kokodeco.brightlyapp.domain.model.Source
import com.kokodeco.brightlyapp.presentation.home.HomeScreen
import com.kokodeco.brightlyapp.presentation.home.HomeState
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_displaysArticles() {
        val articles = listOf(
            Article(
                author = "John Doe",
                title = "Breaking News: Tech Advances",
                description = "Latest updates in technology.",
                content = "Detailed content about the latest technological advancements...",
                publishedAt = "2023-03-01T12:00:00Z",
                source = Source(id = "tech-news", name = "Tech News"),
                url = "https://example.com/tech-advances",
                urlToImage = "https://example.com/image/tech.jpg"
            ),
            Article(
                author = "Jane Smith",
                title = "Global Economy Update",
                description = "Current state of the global economy.",
                content = "In-depth analysis of the global economic trends...",
                publishedAt = "2023-03-02T15:00:00Z",
                source = Source(id = "economy-news", name = "Economy News"),
                url = "https://example.com/economy-update",
                urlToImage = "https://example.com/image/economy.jpg"
            )
            // Add more articles if needed
        )

        val pagingDataFlow = flowOf(PagingData.from(articles))
        composeTestRule.setContent {
            val lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems()
            HomeScreen(
                articles = lazyPagingItems,
                state = HomeState(),
                event = {},
                navigateToSearch = {},
                navigateToDetails = {}
            )
        }

        composeTestRule.waitForIdle()

        articles.forEach { article ->
            composeTestRule.onNodeWithText(article.title, useUnmergedTree = true)
                .assertExists()
        }
    }
}
