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
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.common.components.Overlay
import com.example.common.components.TextFieldError
import com.example.common.components.TextFieldState
import com.example.designsystem.theme.Red
import com.example.designsystem.theme.White
import com.example.feature.auth.R


@Composable
internal fun Email(
    emailState: TextFieldState = remember { EmailState() },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = MaterialTheme.shapes.medium,
            )
            .onFocusChanged { focusState ->
                emailState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    emailState.enableShowErrors()
                }
            },
        value = emailState.text,
        onValueChange = { emailState.text = it },
        textStyle = MaterialTheme.typography.body2,
        singleLine = true,
        maxLines = 1,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            textColor = MaterialTheme.colors.onBackground,
            unfocusedBorderColor = White,
            cursorColor = MaterialTheme.colors.onBackground
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.email),
                style = MaterialTheme.typography.body2
            )
        },
        isError = emailState.showErrors(),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_account),
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
        modifier = Modifier.height(20.dp)
    ) {
        emailState.getError()?.let { error -> TextFieldError(textError = error) }
    }
}

@Composable
internal fun Password(
    modifier: Modifier = Modifier,
    passwordState: TextFieldState = remember { PasswordState() },
    @StringRes hint: Int,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    var showPassword by remember { mutableStateOf(true) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = MaterialTheme.shapes.medium,
                spotColor = Red,
                ambientColor = Red
            )
            .onFocusChanged { focusState ->
                passwordState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    passwordState.enableShowErrors()
                }
            },
        value = passwordState.text,
        onValueChange = { passwordState.text = it },
        textStyle = MaterialTheme.typography.body2,
        singleLine = true,
        maxLines = 1,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            textColor = MaterialTheme.colors.onBackground,
            unfocusedBorderColor = White,
            cursorColor = MaterialTheme.colors.onBackground
        ),
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
                    .clip(CircleShape)
                    .clickable {
                        showPassword = !showPassword
                    },
                imageVector = if (showPassword) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
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
        modifier = Modifier.height(20.dp)
    ) {
        passwordState.getError()?.let { error -> TextFieldError(textError = error) }
    }
}

@Composable
internal fun AuthHeader(
    @StringRes label: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = label),
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.width(4.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_signup),
            contentDescription = null
        )
    }
}


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
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable { onCancelClick() }
                        .padding(12.dp),
                    imageVector = Icons.Outlined.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondary
                )
            }
        }
    }
}

@Composable
internal fun ErrorOverlay(
    message: String,
    onCloseClick: () -> Unit
) {
    Overlay {
        Surface(
            shape = MaterialTheme.shapes.large,
            elevation = 4.dp,
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { onCloseClick() }) {
                    Text(
                        text = stringResource(id = R.string.close),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}