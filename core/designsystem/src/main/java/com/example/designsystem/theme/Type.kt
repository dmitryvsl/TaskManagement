package com.example.designsystem.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.core.designsystem.R


val SDProText = FontFamily(
    Font(R.font.sd_pro_text_regular, weight = FontWeight.Normal),
    Font(R.font.sd_pro_text_bold, weight = FontWeight.Bold),
    Font(R.font.sd_pro_text_medium, weight = FontWeight.Medium),
    Font(R.font.sd_pro_text_semibold, weight = FontWeight.SemiBold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = SDProText,
        fontWeight = FontWeight.Bold,
        fontSize = MaterialTheme.dimens.h1TextSize
    ),
    h2 = TextStyle(
        fontFamily = SDProText,
        fontWeight = FontWeight.Bold,
        fontSize = MaterialTheme.dimens.h2TextSize
    ),
    h3 = TextStyle(
        fontFamily = SDProText,
        fontWeight = FontWeight.Bold,
        fontSize = MaterialTheme.dimens.h3TextSize
    ),
    h4 = TextStyle(
        fontFamily = SDProText,
        fontWeight = FontWeight.Medium,
        fontSize = MaterialTheme.dimens.h4TextSize
    ),
    body1 = TextStyle(
        fontFamily = SDProText,
        fontWeight = FontWeight.Bold,
        fontSize = MaterialTheme.dimens.body1TextSize
    ),
    body2 = TextStyle(
        fontFamily = SDProText,
        fontWeight = FontWeight.Normal,
        fontSize = MaterialTheme.dimens.body2TextSize
    ),
    caption = TextStyle(
        fontFamily = SDProText,
        fontWeight = FontWeight.Medium,
        fontSize = MaterialTheme.dimens.captionTextSize
    ),
)
