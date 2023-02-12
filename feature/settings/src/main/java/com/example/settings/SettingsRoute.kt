package com.example.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.components.TopLevelAppBar
import com.example.designsystem.components.information.ErrorMessageWithAction
import com.example.designsystem.extension.shimmerEffect
import com.example.designsystem.theme.Black
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.dimens
import com.example.domain.exception.NoInternetException
import com.example.domain.model.DataState
import com.example.domain.model.User
import com.example.feature.settings.R

@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateToCreateWorkspace: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        TopLevelAppBar(
            title = stringResource(R.string.settings),
            titleIcon = painterResource(R.drawable.ic_settings_outlined),
            onActionButtonClick = {
                // TODO: Add Action
            },
            modifier = Modifier.padding(horizontal = MaterialTheme.dimens.paddingExtraLarge)
        )

        val data: DataState<User> by viewModel.data.collectAsState()

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingExtraLarge))

        when (data) {
            is DataState.Loading -> SettingsScreenLoading()
            is DataState.Error -> when ((data as DataState.Error<User>).t) {
                is NoInternetException -> ErrorMessageWithAction(
                    message = stringResource(R.string.loadingError),
                    actionMessage = stringResource(R.string.tryAgain),
                    onActionClick = { viewModel.fetchUserInfo() })

                else -> ErrorMessageWithAction(
                    message = stringResource(R.string.errorOccured),
                    actionMessage = stringResource(R.string.tryAgain),
                    onActionClick = { viewModel.fetchUserInfo() })
            }

            is DataState.Success -> SettingsScreen(
                user = (data as DataState.Success<User>).data,
                onAddWorkspaceClick = navigateToCreateWorkspace
            )

            is DataState.Initial -> {}
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SettingsScreen(
    user: User,
    onAddWorkspaceClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimens.paddingExtraLarge)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(MaterialTheme.dimens.minimumTouchTarget),
                shape = CircleShape,
                color = LightGray
            ) {
                Icon(
                    modifier = Modifier.padding(MaterialTheme.dimens.paddingDefault),
                    painter = painterResource(com.example.core.designsystem.R.drawable.ic_account),
                    contentDescription = null,
                    tint = Black
                )
            }

            Spacer(Modifier.width(MaterialTheme.dimens.paddingDefault))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = user.name,
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h2,
                    overflow = TextOverflow.Clip
                )

                Text(
                    text = user.email,
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Clip
                )
            }

            OutlinedButton(
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, color = MaterialTheme.colors.onBackground)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.edit),
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.h4
                    )

                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.paddingSmall))

                    Icon(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = null,
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        }

        Spacer(Modifier.height(MaterialTheme.dimens.paddingDoubleExtraLarge))

        Text(
            text = stringResource(R.string.companyWorkspace),
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.onBackground
        )

        Spacer(Modifier.height(MaterialTheme.dimens.paddingDefault))

        Surface(modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.minimumTouchTarget * 2),
            shape = MaterialTheme.shapes.medium,
            color = LightGray,
            onClick = { onAddWorkspaceClick() }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.addWorkspace),
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.h4
                    )

                    Spacer(Modifier.width(MaterialTheme.dimens.paddingSmall))

                    Icon(
                        imageVector = Icons.Rounded.Add, tint = Black, contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsScreenLoading() {
    Column(
        Modifier.padding(horizontal = MaterialTheme.dimens.paddingExtraLarge)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                Modifier
                    .size(MaterialTheme.dimens.minimumTouchTarget)
                    .clip(CircleShape)
                    .shimmerEffect()
            ) {}

            Spacer(Modifier.width(MaterialTheme.dimens.paddingDefault))

            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.typography.h3.fontSize.value.dp)
                        .shimmerEffect()
                ) {}
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.typography.body2.fontSize.value.dp)
                        .shimmerEffect()
                ) {}
            }
        }

        Spacer(Modifier.height(MaterialTheme.dimens.paddingDoubleExtraLarge))

        Text(
            text = stringResource(R.string.companyWorkspace),
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.onBackground
        )

        Spacer(Modifier.height(MaterialTheme.dimens.paddingDefault))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimens.minimumTouchTarget * 2)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect()
        ) {}
    }
}