package com.example.dashboard.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designsystem.components.card.ProjectCard
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.Purple
import com.example.designsystem.theme.dimens
import com.example.domain.model.Project


@Composable
fun CurrentProject(
    modifier: Modifier,
    project: Project
) {
    val initialOffset = MaterialTheme.dimens.paddingMedium

    Column(
        Modifier
            .fillMaxWidth()
            // 'cause ProjectCard has two less transparent shapes above
            // and shape height equals initialOffset
            // make room for them by multiplying offset by 3
            // Here 3 - 2 for shapes and 1 for extra top padding
            .height(150.dp + initialOffset * 3)
    ) {
        val completedTasksCount =
            remember { project.tasks.filter { task -> task.done }.size }

        Box(
            modifier = Modifier
                .padding(top = initialOffset * 3)
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) {
            ProjectCardTranslucentBackground(initialOffset)

            ProjectCard(
                modifier = modifier,
                title = project.title,
                startDate = project.startDate,
                endDate = project.endDate,
                completedTasks = completedTasksCount,
                totalTasks = project.tasks.size
            )
        }
    }

}

@Composable
private fun ProjectCardTranslucentBackground(
    initialOffset: Dp,
) {
    val isSystemDark = isSystemInDarkTheme()
    val firstLayerColor =
        remember { if (isSystemDark) Purple.copy(alpha = 0.5f) else Gray.copy(alpha = 0.2f) }
    val secondLayerColor =
        remember { if (isSystemDark) Purple.copy(alpha = 0.2f) else LightGray.copy(alpha = 0.5f) }
    repeat(2) { index ->
        val scale = index + 1
        Canvas(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.paddingExtraLarge)
                .height(initialOffset * 2 * scale)
                .padding(horizontal = initialOffset * scale)
                .offset(y = initialOffset * scale * -1f)
        ) {
            drawRoundRect(
                color = if (index == 0) firstLayerColor else secondLayerColor,
                topLeft = Offset.Zero,
                cornerRadius = CornerRadius(16f, 16f),
                size = this.size,
            )
        }
    }
}