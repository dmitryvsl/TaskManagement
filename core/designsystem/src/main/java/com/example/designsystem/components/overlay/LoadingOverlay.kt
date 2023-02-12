package com.example.designsystem.components.overlay

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.designsystem.components.Overlay
import com.example.designsystem.theme.dimens


/**
 * Occupy all screen with transparent overlay and show Progress bar and cancel Icon
 */
@Composable
fun LoadingOverlay(
    onCancelClick: () -> Unit,
) {
    Overlay {
        Surface(
            modifier = Modifier.size(200.dp),
            color = MaterialTheme.colors.background,
            shape = MaterialTheme.shapes.large,
            elevation = 4.dp,
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    strokeWidth = 4.dp,
                    color = MaterialTheme.colors.secondary
                )
                Icon(
                    modifier = Modifier
                        .size(MaterialTheme.dimens.minimumTouchTarget)
                        .clip(CircleShape)
                        .clickable { onCancelClick() }
                        .padding(MaterialTheme.dimens.paddingMedium),
                    imageVector = Icons.Outlined.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondary
                )
            }
        }
    }
}
