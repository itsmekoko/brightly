package com.kokodeco.brightlyapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.kokodeco.brightlyapp.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "Curated positive news",
        description = "Uplifting and joyful stories from a wide spectrum of subjects around the world",
        image = R.drawable.joy1
    ),
    Page(
        title = "Optimistic Insights",
        description = "Celebrating half-full perspectives in global news with hopeful, inspiring stories",
        image = R.drawable.joy2
    )
)
