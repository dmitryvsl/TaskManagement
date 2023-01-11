package com.example.designsystem.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.R
import com.example.designsystem.extension.customShadow
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.dimens

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    icon: Painter,
    title: String,
    date: String
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .customShadow(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colors.background,
        border = BorderStroke(
            width = 1.dp,
            color = if (isSystemInDarkTheme()) MaterialTheme.colors.onBackground else MaterialTheme.colors.background
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.paddingDefault),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(MaterialTheme.dimens.minimumTouchTarget),
                shape = CircleShape,
                color = LightGray,
            ) {
                Icon(
                    modifier = Modifier.padding(MaterialTheme.dimens.paddingSmall),
                    painter = icon,
                    tint = MaterialTheme.colors.onBackground,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.paddingDefault))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onBackground,
                    overflow = TextOverflow.Clip,
                    textAlign = TextAlign.Center
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = Gray
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.paddingSmall))
                    Text(
                        text = "Deadline: $date",
                        color = Gray,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}