package com.example.designsystem.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Dimension(
    val h1TextSize: TextUnit,
    val h2TextSize: TextUnit,
    val h3TextSize: TextUnit,
    val h4TextSize: TextUnit,
    val body1TextSize: TextUnit,
    val body2TextSize: TextUnit,
    val captionTextSize: TextUnit,
    val paddingExtraSmall: Dp,
    val paddingSmall: Dp,
    val paddingMedium: Dp,
    val paddingDefault: Dp,
    val paddingLarge: Dp,
    val paddingExtraLarge: Dp,
    val paddingDoubleExtraLarge: Dp,
    val minimumTouchTarget: Dp = 48.dp,
    val bottomNavBarSize: Dp = 64.dp
)


internal val sw360Dimensions = Dimension(
    h1TextSize = 22.sp,
    h2TextSize = 16.sp,
    h3TextSize = 14.sp,
    h4TextSize = 14.sp,
    body1TextSize = 12.sp,
    body2TextSize = 12.sp,
    captionTextSize = 10.sp,
    paddingExtraSmall = 2.dp,
    paddingSmall = 4.dp,
    paddingMedium = 8.dp,
    paddingDefault = 12.dp,
    paddingLarge = 16.dp,
    paddingExtraLarge = 20.dp,
    paddingDoubleExtraLarge = 40.dp,
)

internal val sw600Dimension = Dimension(
    h1TextSize = 24.sp,
    h2TextSize = 18.sp,
    h3TextSize = 18.sp,
    h4TextSize = 18.sp,
    body1TextSize = 14.sp,
    body2TextSize = 14.sp,
    captionTextSize = 12.sp,
    paddingExtraSmall = 4.dp,
    paddingSmall = 8.dp,
    paddingMedium = 12.dp,
    paddingDefault = 16.dp,
    paddingLarge = 20.dp,
    paddingExtraLarge = 24.dp,
    paddingDoubleExtraLarge = 48.dp
)