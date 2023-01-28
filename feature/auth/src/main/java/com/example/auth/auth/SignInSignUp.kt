package com.example.auth.auth

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.common.BaseViewModel
import com.example.common.DataState
import com.example.designsystem.components.Overlay
import com.example.designsystem.components.textfield.TextFieldError
import com.example.designsystem.components.textfield.TmOutlinedTextField
import com.example.designsystem.components.textfield.TextFieldState
import com.example.designsystem.theme.dimens
import com.example.domain.exception.*
import com.example.feature.auth.R


/**
 * Textfield for entering email
 */
@Composable
internal fun Email(
    modifier: Modifier = Modifier,
    emailState: TextFieldState = remember { EmailState() },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    TmOutlinedTextField(
        modifier = modifier,
        value = emailState.text,
        onValueChange = { emailState.text = it },
        onFocusChanged = { focusState ->
            emailState.onFocusChange(focusState.isFocused)
            if (!focusState.isFocused) emailState.enableShowErrors()
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.email),
                style = MaterialTheme.typography.body2
            )
        },
        isError = emailState.showErrors(),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_mail),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(onDone = { onImeAction() }),
    )
    Box(
        modifier = modifier.height(MaterialTheme.dimens.paddingExtraLarge)
    ) {
        emailState.getError()?.let { error -> TextFieldError(textError = error) }
    }
}

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
 * Occupy all screen with transparent overlay and show Progress bar and cancel Icon
 */
@Composable
internal fun LoadingOverlay(
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

/**
 * Occupy all screen with transparent overlay and show message
 */
@Composable
internal fun InformationDialog(
    message: String,
    onCloseClick: () -> Unit
) {
    AlertDialog(
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
        },
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.background,
        onDismissRequest = onCloseClick,
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Button(onClick = { onCloseClick() }) {
                    Text(
                        text = stringResource(id = R.string.close),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    )
}


/**
 * Represent Data state: Loading, Error, Success. Show information dialogs according to state
 */
@Composable
fun CallStateRepresenter(
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