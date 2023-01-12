package com.example.designsystem.components.information

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun Loading(
    modifier: Modifier,
    color: Color = MaterialTheme.colors.secondary,
    animationDuration: Int = 1500
) {
    val infiniteTransition = rememberInfiniteTransition()
    val innerCircleAnimation by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val outerCircleAnimation by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 0f, animationSpec = infiniteRepeatable(
            animation = tween(animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        repeat(2) { index ->
            Canvas(Modifier.size(16.dp)) {
                drawCircle(
                    color = color,
                    style = Stroke(width = 2.dp.toPx()),
                    radius = if (index == 0) innerCircleAnimation.dp.toPx() * (size.height / 2) else outerCircleAnimation.dp.toPx() * (size.height / 2),
                    alpha = if (index == 0) innerCircleAnimation else outerCircleAnimation
                )
            }
        }
    }
}