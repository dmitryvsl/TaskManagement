package com.example.designsystem.extension

import android.os.Build
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.scale
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.Dark
import com.example.designsystem.theme.LightGray
import com.example.designsystem.utils.animationDuration

fun Modifier.drawColoredShadow(
    color: Color = Color.Black,
    alpha: Float = 0.07f,
    borderRadius: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 7.dp,
    spread: Dp = 0.dp,
) = this.drawBehind {
    val transparentColor = color.copy(alpha = 0.0f).toArgb()
    val shadowColor = color.copy(alpha = alpha).toArgb()
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            blurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.save()

        if (spread.value > 0) {
            fun calcSpreadScale(spread: Float, childSize: Float): Float {
                return 1f + ((spread / childSize) * 2f)
            }

            it.scale(
                calcSpreadScale(spread.toPx(), this.size.width),
                calcSpreadScale(spread.toPx(), this.size.height),
                this.center.x,
                this.center.y
            )
        }

        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            borderRadius.toPx(),
            borderRadius.toPx(),
            paint
        )
        it.restore()
    }
}

fun Modifier.customShadow(): Modifier = composed {
    val mediumShape = MaterialTheme.shapes.medium
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
        this.drawColoredShadow(color = Dark)
    else
        this.shadow(
            elevation = 4.dp,
            shape = mediumShape,
            spotColor = Dark.copy(alpha = 0.92f),
            ambientColor = Dark.copy(alpha = 0.92f),
        )
}

fun Modifier.shimmerEffect() = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(tween(durationMillis = animationDuration))
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                LightGray.copy(0.9f),
                LightGray.copy(0.3f),
                LightGray.copy(0.9f),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}
