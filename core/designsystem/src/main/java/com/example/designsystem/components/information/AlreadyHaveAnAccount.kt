package com.example.designsystem.components.information

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.dimens


@Composable
fun AuthOtherWay(
    modifier: Modifier = Modifier,
    @StringRes hint: Int,
    @StringRes authWayName: Int,
    onAuthWayClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = hint),
            style = MaterialTheme.typography.h4,
            color = Gray
        )
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.paddingExtraSmall))
        Text(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable { onAuthWayClick() },
            text = stringResource(id = authWayName),
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.secondary
        )
    }
}