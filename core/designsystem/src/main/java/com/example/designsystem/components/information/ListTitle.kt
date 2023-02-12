package com.example.designsystem.components.information

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.core.designsystem.R
import com.example.designsystem.theme.dimens

@Composable
fun ListTitle(
    title: String,
    titleIcon: Painter,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = titleIcon,
            contentDescription = null,
            tint = MaterialTheme.colors.secondary,
        )
        Spacer(Modifier.width(MaterialTheme.dimens.paddingSmall))
        Text(
            text = title,
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onBackground,

            )
        Spacer(Modifier.weight(1f))
        Icon(
            painter = painterResource(R.drawable.ic_forward),
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) MaterialTheme.colors.onBackground else MaterialTheme.colors.primary,
        )

    }
}