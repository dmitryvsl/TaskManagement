package com.example.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.designsystem.components.FeatureInDevelopment
import com.example.designsystem.components.TopLevelAppBar
import com.example.feature.chat.R

@Composable
fun ChatRoute() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        TopLevelAppBar(
            title = stringResource(R.string.chat),
            titleIcon = painterResource(R.drawable.ic_chat_outlined)
        ) {
            // TODO: Add action
        }
        FeatureInDevelopment()
    }
}