package com.example.designsystem.components.card

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core.designsystem.R
import com.example.designsystem.extension.customShadow
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.dimens
import com.example.designsystem.utils.animationDuration
import com.example.domain.model.Project
import com.example.domain.model.User
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectCard(
    project: Project,
    modifier: Modifier = Modifier,
    overlay: @Composable () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .customShadow(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colors.background,
        border = BorderStroke(
            width = 1.dp,
            color = if (isSystemInDarkTheme()) MaterialTheme.colors.onBackground else MaterialTheme.colors.background
        )
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .combinedClickable(
                onClick = {},
                onLongClick = onLongClick
            )
        ) {
            overlay()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.dimens.paddingExtraLarge),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                val completedTasksCount =
                    remember { project.tasks.filter { task -> task.done }.size }

                ProjectTitleAndMembers(title = project.title, members = project.members)
                ProjectCalendarLine(
                    startDate = project.startDate,
                    endDate = project.endDate
                )
                ProjectProgress(
                    completedTasks = completedTasksCount,
                    totalTasks = project.tasks.size
                )
            }
        }

    }
}

@Composable
private fun ProjectTitleAndMembers(
    title: String,
    members: List<User>
) {
    val painter = painterResource(R.drawable.avatar_placeholder)
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.onBackground,
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                members.take(3).forEachIndexed { index, user ->
                    val offsetX = (MaterialTheme.dimens.minimumTouchTarget / 2) * index * -1f
                    AsyncImage(
                        modifier = Modifier
                            .offset(offsetX)
                            .zIndex(offsetX.value)
                            .size(MaterialTheme.dimens.minimumTouchTarget / 1.5f)
                            .clip(CircleShape),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(user.photo)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        placeholder = painter
                    )
                }

                val restTeammatesCount = remember { members.size - 3 }
                if (restTeammatesCount > 0) {
                    Box(
                        modifier = Modifier
                            .zIndex(4f)
                            .size(MaterialTheme.dimens.minimumTouchTarget / 1.5f)
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.secondary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+$restTeammatesCount",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSecondary
                        )
                    }
                }
            }


        }
    }
}

@Composable
private fun CalendarIconAndDate(
    date: String,
    color: Color
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_calendar),
            contentDescription = null,
            tint = color
        )
        Spacer(Modifier.width(MaterialTheme.dimens.paddingSmall))
        Text(
            text = date,
            style = MaterialTheme.typography.body2,
            color = color
        )
    }
}

@Composable
private fun ProjectCalendarLine(
    startDate: String,
    endDate: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimens.paddingSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CalendarIconAndDate(
            date = startDate,
            color = Gray
        )
        CalendarArrow(
            modifier = Modifier
                .weight(1f)
                .height(MaterialTheme.dimens.paddingDefault)
                .padding(horizontal = MaterialTheme.dimens.paddingExtraSmall)
        )
        CalendarIconAndDate(
            date = endDate,
            color = MaterialTheme.colors.primary
        )

    }
}

@Composable
fun CalendarArrow(
    modifier: Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        var initialDashedLineOffset by rememberSaveable { mutableStateOf(0f) }
        var initialTriangleOffset by rememberSaveable { mutableStateOf(0f) }
        val endDashedLineOffsetAnimation by animateDpAsState(
            initialDashedLineOffset.dp,
            tween(animationDuration)
        )
        val endTriangleOffsetAnimation by animateDpAsState(
            initialTriangleOffset.dp,
            tween(animationDuration)
        )
        val maxWidth = this.maxWidth
        val maxHeight = this.maxHeight
        DrawCircle(modifier)
        DrawDashedLine(
            Modifier
                .size(endDashedLineOffsetAnimation, this.maxHeight)
                .offset(maxHeight + MaterialTheme.dimens.paddingSmall)
                .padding(end = maxHeight + MaterialTheme.dimens.paddingSmall)
        )
        DrawTriangle(
            Modifier
                .size(maxHeight)
                .offset(maxHeight + endTriangleOffsetAnimation)
        )
        LaunchedEffect(Unit) {
            initialDashedLineOffset = maxWidth.value - maxHeight.value
            initialTriangleOffset = maxWidth.value - maxHeight.value * 2
        }
    }
}

@Composable
fun ProjectProgress(
    totalTasks: Int,
    completedTasks: Int
) {
    val progress =
        if (totalTasks != 0) remember { (completedTasks / totalTasks.toFloat() * 100).roundToInt() } else remember { 100 }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$progress%",
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onBackground
        )
        BoxWithConstraints(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.paddingMedium)
                .weight(1f)
                .height(MaterialTheme.dimens.paddingDefault),
        ) {
            var shouldShowAnimation by rememberSaveable { mutableStateOf(false) }
            val progressLineAnimation by animateDpAsState(
                targetValue = if (shouldShowAnimation) maxWidth / 100 * progress else 0.dp,
                tween(animationDuration)
            )
            DrawLine(modifier = Modifier.fillMaxSize())
            DrawLine(
                modifier = Modifier
                    .width(progressLineAnimation)
                    .fillMaxHeight(),
                color = MaterialTheme.colors.primary
            )
            LaunchedEffect(Unit) {
                shouldShowAnimation = true
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = completedTasks.toString(),
                style = MaterialTheme.typography.caption,
                color = Gray,
            )

            Text(
                text = "/$totalTasks tasks",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onBackground,
            )
        }
    }
}

@Composable
private fun DrawCircle(modifier: Modifier, color: Color = Gray) {

    Canvas(modifier) {
        val height = this.size.height
        drawCircle(
            color = color,
            radius = height / 2,
            center = Offset(height / 2, height / 2)
        )
    }
}

@Composable
fun DrawLine(modifier: Modifier, color: Color = LightGray) {
    Canvas(modifier) {
        drawRoundRect(
            color = color,
            size = this.size,
            topLeft = Offset.Zero,
            cornerRadius = CornerRadius(this.size.height / 2, this.size.height / 2)
        )
    }
}

@Composable
fun DrawDashedLine(modifier: Modifier, color: Color = Gray) {
    Canvas(modifier) {
        val pathEffect =
            PathEffect.dashPathEffect(floatArrayOf(this.size.width / 6, this.size.width / 6), 0f)
        val height = size.height
        val width = size.width
        drawLine(
            color = color,
            strokeWidth = this.size.height / 3,
            start = Offset(0f, height / 2),
            end = Offset(width, height / 2),
            pathEffect = pathEffect
        )
    }
}

@Composable
fun DrawTriangle(
    modifier: Modifier,
    color: Color = Gray
) {
    Canvas(modifier) {
        val height = this.size.height
        val width = this.size.width
        val rect = Rect(Offset(width - height, 0f), Size(height, height))
        val trianglePath = Path().apply {
            moveTo(rect.topCenter.x, rect.topCenter.y)
            lineTo(rect.bottomRight.x, rect.bottomRight.y)
            lineTo(rect.bottomLeft.x, rect.bottomLeft.y)
            close()
        }

        rotate(degrees = 90f, rect.center) {
            drawIntoCanvas { canvas ->
                canvas.drawOutline(
                    outline = Outline.Generic(trianglePath),
                    paint = Paint().apply {
                        this.color = color
                        this.pathEffect = PathEffect.cornerPathEffect(rect.maxDimension / 3)
                    }
                )
            }
        }
    }
}