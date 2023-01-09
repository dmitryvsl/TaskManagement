package com.example.designsystem.extension

import android.os.Build
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.scale
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.Dark

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
