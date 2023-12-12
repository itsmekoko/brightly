package com.kokodeco.brightlyapp.presentation.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.kokodeco.brightlyapp.R
import com.kokodeco.brightlyapp.domain.model.Article
import com.kokodeco.brightlyapp.presentation.core.ArticlesList
import com.kokodeco.brightlyapp.util.Dimens
import com.kokodeco.brightlyapp.util.Dimens.MediumPadding
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToDetails: (Article) -> Unit
) {
    val articles = viewModel.news.collectAsLazyPagingItems()
    val isNetworkAvailable = viewModel.isNetworkAvailable.collectAsState().value

    val logoResId = if (isSystemInDarkTheme()) {
        R.drawable.ic_logo_dark
    } else {
        R.drawable.ic_logo
    }

    val titles by remember {
        derivedStateOf {
            if (articles.itemCount > 10) {
                articles.itemSnapshotList.items
                    .slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = " \uD83E\uDD13 ") { it.title }
            } else {
                ""
            }
        }
    }

    val scrollState = rememberScrollState(initial = viewModel.state.value.scrollValue)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MediumPadding)
            .statusBarsPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = Dimens.ExtraSmallPaddingTwo)
        ) {
            Image(
                painter = painterResource(id = logoResId),
                contentDescription = null,
                modifier = Modifier
                    .width(60.dp)
                    .height(50.dp)
            )
            Spacer(modifier = Modifier.width(Dimens.ExtraSmallPaddingTwo))
            Text(
                text = "BRIGHTLY",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(MediumPadding))

        if (!isNetworkAvailable && articles.itemSnapshotList.items.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MediumPadding)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No internet connection available. Please retry!",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(MediumPadding))
                    Button(
                        onClick = { viewModel.reloadNews() },
                        colors = ButtonDefaults.buttonColors(Color.DarkGray)
                    ) {
                        Text("Reload", color = Color.White)
                    }
                }
            }
        }

        Text(
            text = titles,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = MediumPadding)
                .horizontalScroll(scrollState, enabled = false),
            fontSize = 16.sp,
            color = if (isSystemInDarkTheme()) {
                colorResource(id = R.color.placeholder)
            } else {
                Color.Black
            }
        )

        LaunchedEffect(key1 = scrollState.maxValue) {
            viewModel.onEvent(HomeEvent.UpdateMaxScrollingValue(scrollState.maxValue))
        }

        LaunchedEffect(key1 = scrollState.value) {
            viewModel.onEvent(HomeEvent.UpdateScrollValue(scrollState.value))
        }

        LaunchedEffect(key1 = viewModel.state.value.maxScrollingValue) {
            delay(500)
            if (viewModel.state.value.maxScrollingValue > 0) {
                scrollState.animateScrollTo(
                    value = viewModel.state.value.maxScrollingValue,
                    animationSpec = infiniteRepeatable(
                        tween(
                            durationMillis = (viewModel.state.value.maxScrollingValue - viewModel.state.value.scrollValue) * 50_000 / viewModel.state.value.maxScrollingValue,
                            easing = LinearEasing,
                            delayMillis = 1000
                        )
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(MediumPadding))

        ArticlesList(
            modifier = Modifier.padding(horizontal = MediumPadding),
            articles = articles,
            onClick = navigateToDetails
        )
    }
}
