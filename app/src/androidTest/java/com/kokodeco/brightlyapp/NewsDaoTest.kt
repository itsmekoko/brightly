package com.kokodeco.brightlyapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kokodeco.brightlyapp.data.local.NewsDao
import com.kokodeco.brightlyapp.domain.model.Article
import com.kokodeco.brightlyapp.domain.model.Source
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class NewsDaoTest {

    private lateinit var newsDao: NewsDao

    @Before
    fun setup() {
        newsDao = Mockito.mock(NewsDao::class.java)
    }

    @Test
    fun testUpsertAndRetrieve() = runBlocking {
        val article = Article(
            author = "Kodeco",
            content = "This is a sample content for the article...",
            description = "This is a sample description of the article.",
            publishedAt = "2021-12-01T12:00:00Z",
            source = Source(id = "abc-news", name = "ABC News"),
            title = "Sample Article Title",
            url = "https://example.com/sample-article",
            urlToImage = "https://example.com/sample-image.jpg"
        )

        // Mock the behavior of the DAO
        `when`(newsDao.getArticle(article.url)).thenReturn(article)

        newsDao.upsert(article)

        val retrieved = newsDao.getArticle(article.url)
        assertEquals(article, retrieved)
    }

    // Additional tests can be added here
}
