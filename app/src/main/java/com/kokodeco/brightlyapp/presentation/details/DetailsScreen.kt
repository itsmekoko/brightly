package com.kokodeco.brightlyapp.presentation.details

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kokodeco.brightlyapp.R
import com.kokodeco.brightlyapp.domain.model.Article
import com.kokodeco.brightlyapp.domain.model.Source
import com.kokodeco.brightlyapp.presentation.details.components.DetailsTopBar
import com.kokodeco.brightlyapp.ui.theme.NewsAppTheme
import com.kokodeco.brightlyapp.util.UIComponent

@Composable
fun DetailsScreen(
    article: Article,
    event: (DetailsEvent) -> Unit,
    sideEffect: UIComponent?,
    navigateUp: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel() // Inject ViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = article) {
        viewModel.checkBookmarkStatus(article.url)
    }

    val isBookmarked = viewModel.isArticleBookmarked
    LaunchedEffect(key1 = sideEffect) {
        sideEffect?.let {
            when (it) {
                is UIComponent.Toast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    event(DetailsEvent.RemoveSideEffect)
                }

                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
            isBookmarked = isBookmarked,
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = Uri.parse(article.url)
                    context.startActivity(it)
                }
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, article.url)
                    it.type = "text/plain"
                    context.startActivity(Intent.createChooser(it, null))
                }
            },
            onBookMarkClick = {
                event(DetailsEvent.UpsertDeleteArticle(article))
            },
            onBackClick = navigateUp
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(all = 16.dp)
        ) {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(context).data(article.urlToImage).build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(id = R.color.text_title)
                )
                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.body)
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    NewsAppTheme(dynamicColor = false) {
        DetailsScreen(
            article = Article(
                author = "",
                title = "Sample Article Title",
                description = "Sample Article Description",
                content = "Sample Article Content",
                publishedAt = "2023-01-01T00:00:00Z",
                source = Source(id = "1", name = "Sample Source"),
                url = "https://example.com",
                urlToImage = "https://example.com/image.jpg",
                isBookmarked = false // Added for the preview
            ),
            event = {},
            sideEffect = null,
            navigateUp = {}
        )
    }
}