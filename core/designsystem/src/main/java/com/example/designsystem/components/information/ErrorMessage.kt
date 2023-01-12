package com.example.designsystem.components.information

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.designsystem.theme.dimens

@Composable
fun ErrorMessageWithAction(
    message: String,
    actionMessage: String,
    onActionClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingSmall))
        Button(onClick = onActionClick) {
            Text(
                text = actionMessage,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center
            )
        }
    }
}