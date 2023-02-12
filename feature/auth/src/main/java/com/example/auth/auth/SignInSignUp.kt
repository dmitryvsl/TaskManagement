package com.example.auth.auth

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.common.base.BaseViewModel
import com.example.common.base.DataState
import com.example.designsystem.components.overlay.InformationDialog
import com.example.designsystem.components.overlay.LoadingOverlay
import com.example.designsystem.components.textfield.TextFieldError
import com.example.designsystem.components.textfield.TextFieldState
import com.example.designsystem.components.textfield.TmOutlinedTextField
import com.example.designsystem.theme.dimens
import com.example.domain.exception.IncorrectDataException
import com.example.domain.exception.NoInternetException
import com.example.domain.exception.UserAlreadyExist
import com.example.domain.exception.UserAuthException
import com.example.domain.exception.UserNotExist
import com.example.feature.auth.R


/**
 * Textfield for entering password
 */
@Composable
internal fun Password(
    modifier: Modifier = Modifier,
    passwordState: TextFieldState = remember { PasswordState() },
    @StringRes hint: Int,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    var showPassword by remember { mutableStateOf(true) }
    TmOutlinedTextField(
        modifier = modifier,
        value = passwordState.text,
        onValueChange = { passwordState.text = it },
        onFocusChanged = { focusState ->
            passwordState.onFocusChange(focusState.isFocused)
            if (!focusState.isFocused) {
                passwordState.enableShowErrors()
            }
        },
        placeholder = {
            Text(
                text = stringResource(id = hint),
                style = MaterialTheme.typography.body2
            )
        },
        isError = passwordState.showErrors(),
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_password),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .size(MaterialTheme.dimens.minimumTouchTarget / 2f)
                    .clip(CircleShape)
                    .clickable {
                        showPassword = !showPassword
                    },
                painter = painterResource(id = if (showPassword) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onDone = { onImeAction() })
    )
    Box(
        modifier = modifier.height(MaterialTheme.dimens.paddingExtraLarge)
    ) {
        passwordState.getError()?.let { error -> TextFieldError(textError = error) }
    }
}


/**
 * Header with Icon
 */
@Composable
internal fun AuthHeader(
    modifier: Modifier = Modifier,
    @StringRes label: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = label),
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.paddingSmall))
        Image(
            painter = painterResource(id = R.drawable.ic_signup),
            contentDescription = null
        )
    }
}

/**
 * Represent Data state: Loading, Error, Success. Show information dialogs according to state
 */
@Composable
fun DataStateRepresenter(
    viewModel: BaseViewModel<Boolean>,
    onCallSuccess: () -> Unit,
    noInternetExceptionMessage: String = stringResource(R.string.noInternetConnection),
    invalidEmailOrPasswordExceptionMessage: String = stringResource(R.string.invalidEmailOrPassword),
    userAuthExceptionMessage: String = stringResource(R.string.userCreateError),
    userAlreadyExistMessage: String = stringResource(R.string.userAlreadyExist),
    userNotExistMessage: String = stringResource(R.string.enteredEmailDoesntExist),
    undefinedExceptionMessage: String = stringResource(R.string.someErrorOccured)
) {
    val state: DataState<Boolean> by viewModel.data.observeAsState(DataState.Initial())

    val onError: @Composable (message: String) -> Unit = { message ->
        InformationDialog(message = message) { viewModel.clearState() }
    }

    when (state){
        is DataState.Error -> when ((state as DataState.Error<Boolean>).t) {
            is NoInternetException -> onError(noInternetExceptionMessage)
            is IncorrectDataException -> onError(invalidEmailOrPasswordExceptionMessage)
            is UserAuthException -> onError(userAuthExceptionMessage)
            is UserAlreadyExist -> onError(userAlreadyExistMessage)
            is UserNotExist -> onError(userNotExistMessage)
            else -> onError(undefinedExceptionMessage)
        }
        is DataState.Loading -> LoadingOverlay {
            viewModel.clearState()
        }
        is DataState.Success -> onCallSuccess()
        is DataState.Initial -> {}
    }
}