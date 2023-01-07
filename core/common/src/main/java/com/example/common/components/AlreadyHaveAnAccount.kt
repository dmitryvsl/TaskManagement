package com.example.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.common.R
import com.example.designsystem.theme.Gray


@Composable
fun AlreadyHaveAnAccount(
    onSignInClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.alreadyHaveAnAccount),
            style = MaterialTheme.typography.h4,
            color = Gray
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable { onSignInClick() },
            text = stringResource(id = R.string.signIn),
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.secondary
        )
    }
}