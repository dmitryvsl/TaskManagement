package com.example.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.components.TopLevelAppBar
import com.example.feature.dashboard.R

@Composable
fun DashboardRoute() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        TopLevelAppBar(
            title = stringResource(R.string.dashboard),
            titleIcon = painterResource(R.drawable.ic_dashboard)
        ) {
            // TODO: Add Action
        }
    }


}