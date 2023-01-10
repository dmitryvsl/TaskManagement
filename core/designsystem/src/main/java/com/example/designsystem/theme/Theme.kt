package com.example.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

private val DarkColorPalette = darkColors(
    primary = Blue,
    onPrimary = White,
    secondary = Orange,
    onSecondary = White,
    background = Dark,
    onBackground = White,
    error = Red
)

private val LightColorPalette = lightColors(
    primary = Blue,
    onPrimary = White,
    secondary = Orange,
    onSecondary = White,
    background = White,
    onBackground = Black,
    error = Red

)

private var Dimensions = sw600Dimension

@Composable
fun TaskManagementTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val configuration = LocalConfiguration.current
    Dimensions = when {
        configuration.smallestScreenWidthDp <= 360 -> sw360Dimensions
        else -> sw600Dimension
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}

val MaterialTheme.dimens
    get() = Dimensions