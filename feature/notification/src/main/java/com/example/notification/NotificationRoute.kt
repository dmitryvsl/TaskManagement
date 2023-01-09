package com.example.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.designsystem.components.FeatureInDevelopment
import com.example.designsystem.components.TopLevelAppBar
import com.example.feature.notification.R

@Composable
fun NotificationRoute(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        TopLevelAppBar(
            title = stringResource(R.string.notification),
            titleIcon = painterResource(R.drawable.ic_notification_outlined)
        ) {
            // TODO: Add action
        }
        FeatureInDevelopment()
    }
}