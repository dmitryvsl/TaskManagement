package com.example.onboarding.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.onboarding.R

data class Page(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int,
)

val onboardPages = listOf(
    Page(
        title = R.string.title1,
        description = R.string.description1,
        image = R.drawable.onboarding1
    ),
    Page(
        title = R.string.title2,
        description = R.string.description2,
        image = R.drawable.onboarding2
    ),
    Page(
        title = R.string.title3,
        description = R.string.description3,
        image = R.drawable.onboarding3
    )
)