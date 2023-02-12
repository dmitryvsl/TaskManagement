package com.example.settings.createworkspace

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.base.DataState
import com.example.designsystem.components.appbar.TmTopAppBar
import com.example.designsystem.components.overlay.InformationDialog
import com.example.designsystem.components.overlay.LoadingOverlay
import com.example.designsystem.components.textfield.Email
import com.example.designsystem.components.textfield.EmailState
import com.example.designsystem.components.textfield.TextFieldError
import com.example.designsystem.components.textfield.TmOutlinedTextField
import com.example.designsystem.theme.dimens
import com.example.domain.exception.NoInternetException
import com.example.feature.settings.R

@Composable
fun CreateWorkspaceScreen(
    viewModel: CreateWorkspaceViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val data by viewModel.data.observeAsState(DataState.Initial())

    Box(Modifier.fillMaxSize()) {
        when (data) {
            is DataState.Loading -> LoadingOverlay { viewModel.cancelLoading() }
            is DataState.Error -> when ((data as DataState.Error<Boolean>).t) {
                is NoInternetException -> InformationDialog(message = stringResource(R.string.noInternet)) {
                    viewModel.clearState()
                }

                else -> InformationDialog(message = stringResource(R.string.errorOccured)) {
                    viewModel.clearState()
                }
            }

            is DataState.Success -> onBackClick()
            is DataState.Initial -> {}
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colors.background)
                .padding(horizontal = MaterialTheme.dimens.paddingExtraLarge)
                .statusBarsPadding()
                .fillMaxSize()
                .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
        ) {
            val nameState = remember { NameState() }
            val emailState = remember { EmailState() }

            TmTopAppBar(title = stringResource(R.string.createWorkspace), onBackClick = onBackClick)

            TextFieldTitle(stringResource(R.string.name))

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingDefault))

//            Name(
//                nameState = nameState,
//                modifier = Modifier.foc
//            )

            TextFieldTitle(stringResource(R.string.email))

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingDefault))

            Email(
                emailState = emailState,
                imeAction = ImeAction.Done,
                onImeAction = { focusManager.clearFocus() }
            )

            Spacer(Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding(),
                enabled = nameState.isValid && emailState.isValid,
                onClick = { viewModel.createWorkspace(nameState.text, emailState.text) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = stringResource(R.string.create),
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.body2
                )
            }

        }
    }
}

@Composable
private fun TextFieldTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.h4,
        color = MaterialTheme.colors.onBackground,
    )
}


@Composable
fun Name(
    modifier: Modifier = Modifier,
    nameState: NameState = remember { NameState() },
) {
    TmOutlinedTextField(
        modifier = modifier,
        onFocusChanged = { focusState ->
            nameState.onFocusChange(focusState.isFocused)
            if (!focusState.isFocused) nameState.enableShowErrors()
        },
        value = nameState.text,
        onValueChange = { nameState.text = it },
        isError = nameState.showErrors(),
        leadingIcon = {
            Icon(
                painter = painterResource(com.example.core.designsystem.R.drawable.ic_account),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.name),
                style = MaterialTheme.typography.body2
            )

        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
    )
    Box(
        modifier = Modifier.height(MaterialTheme.dimens.paddingExtraLarge)
    ) {
        nameState.getError()?.let { error -> TextFieldError(textError = error) }
    }
}
