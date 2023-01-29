package com.example.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.core.designsystem.R
import com.example.designsystem.theme.White
import com.example.designsystem.theme.dimens

@Composable
fun BookmarkOverlay(
    isBookmarked: Boolean,
    onCloseClick: () -> Unit,
    onBookmarkClick: () -> Unit,
) {
    val icon = if (isBookmarked) R.drawable.ic_unflag else R.drawable.ic_flag
    val text = if (isBookmarked) R.string.unflag else R.string.flag
    Overlay{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium),
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .padding(
                        top = MaterialTheme.dimens.paddingSmall,
                        end = MaterialTheme.dimens.paddingSmall
                    )
                    .clickable { onCloseClick() },
                painter = painterResource(R.drawable.ic_cancel_circle),
                contentDescription = null,
                tint = White,
            )
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable { onBookmarkClick() },
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = White
                )
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.paddingExtraSmall))
                Text(
                    text = stringResource(text),
                    style = MaterialTheme.typography.h3,
                    color = White
                )
            }
        }
    }
}