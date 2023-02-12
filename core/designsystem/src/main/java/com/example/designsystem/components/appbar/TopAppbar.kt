package com.example.designsystem.components.appbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.designsystem.theme.dimens


@Composable
fun TmTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    action: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        Icon(modifier = Modifier
            .size(MaterialTheme.dimens.minimumTouchTarget)
            .clip(CircleShape)
            .clickable { onBackClick() }
            .padding(MaterialTheme.dimens.paddingMedium)
            .align(Alignment.CenterStart),
            imageVector = Icons.Rounded.KeyboardArrowLeft,
            contentDescription = null,
            tint = MaterialTheme.colors.onBackground)
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground
        )
        Box(Modifier.align(Alignment.CenterEnd)) {
            action()
        }
    }
}