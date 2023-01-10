package com.example.designsystem.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.White
import com.example.designsystem.theme.dimens

@Composable
fun TopLevelAppBar(
    title: String,
    titleIcon: Painter,
    onActionButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground,
        )
        Spacer(Modifier.width(MaterialTheme.dimens.paddingSmall))
        Icon(
            painter = titleIcon,
            contentDescription = null,
            tint = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier.size(MaterialTheme.dimens.minimumTouchTarget),
            onClick = onActionButtonClick,
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isSystemInDarkTheme()) MaterialTheme.colors.secondary else MaterialTheme.colors.primary
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = White
            )
        }
    }

}