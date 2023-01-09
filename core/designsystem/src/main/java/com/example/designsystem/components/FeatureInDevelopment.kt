package com.example.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.designsystem.R
import com.example.designsystem.theme.TaskManagementTheme

@Composable
fun FeatureInDevelopment() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(220.dp),
            painter = painterResource(R.drawable.development),
            contentDescription = null
        )

        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.inDevelopment),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FeatureInDevelopmentPreview(){
    TaskManagementTheme{
        FeatureInDevelopment()
    }
}