package com.kokodeco.brightlyapp.presentation.details.components

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kokodeco.brightlyapp.R
import com.kokodeco.brightlyapp.ui.theme.NewsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    isBookmarked: Boolean,
    onBrowsingClick: () -> Unit,
    onShareClick: () -> Unit,
    onBookMarkClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var animateIcon by remember { mutableStateOf(false) }
    val triggerAnimation = remember { mutableStateOf(false) }

    val bookmarkTransition = updateTransition(targetState = triggerAnimation.value, label = "bookmark_transition")

    val scaleAnimation by bookmarkTransition.animateFloat(
        label = "bookmark_scale",
        transitionSpec = {
            if (triggerAnimation.value) {
                keyframes {
                    durationMillis = 1000
                    1.0f at 0 with FastOutSlowInEasing
                    0.75f at 400 with FastOutSlowInEasing
                    1.5f at 700 with FastOutSlowInEasing
                    1.0f at 1000 with FastOutSlowInEasing
                }
            } else {
                tween(durationMillis = 300)
            }
        }
    ) { state ->
        if (state) 1.0f else 1.0f
    }

    val rotationAnimation by bookmarkTransition.animateFloat(
        label = "bookmark_rotation",
        transitionSpec = { tween(durationMillis = 750) }
    ) { state -> if (state) 360.0f else 0.0f }

    LaunchedEffect(isBookmarked) {
        if (animateIcon) {
            triggerAnimation.value = !triggerAnimation.value
            animateIcon = false
        }
    }

    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor = colorResource(id = R.color.body),
            navigationIconContentColor = colorResource(id = R.color.body)
        ),
        title = {},
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = {
                onBookMarkClick()
                animateIcon = true
            }) {
                Icon(
                    painter = painterResource(id = if (isBookmarked) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(32.dp)
                        .graphicsLayer(
                            scaleX = scaleAnimation,
                            scaleY = scaleAnimation,
                            rotationZ = rotationAnimation
                        )
                )
            }
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null
                )
            }
            IconButton(onClick = onBrowsingClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_network),
                    contentDescription = null
                )
            }
        }
    )
}
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailsTopBarPreview() {
    NewsAppTheme(dynamicColor = false) {
        DetailsTopBar(
            isBookmarked = true, // For preview purposes
            onShareClick = { /*TODO*/ },
            onBookMarkClick = { /*TODO*/ },
            onBrowsingClick = {},
            onBackClick = { /*TODO*/ }
        )
    }
}