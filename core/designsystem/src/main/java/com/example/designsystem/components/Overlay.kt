package com.example.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.example.designsystem.theme.Dark

@Composable
fun Overlay(
    color: Color = Dark.copy(alpha = 0.1f),
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(interactionSource = MutableInteractionSource(), indication = null) { }
            .zIndex(1f)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}