package com.example.auth.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth.R
import com.example.designsystem.components.TextFieldState
import com.example.designsystem.theme.Red
import com.example.designsystem.theme.White

@Composable
fun SignUpRoute() {
    val loginState by rememberSaveable(stateSaver = LoginStateSaver) {
        mutableStateOf(LoginState())
    }
    val passwordState by rememberSaveable(stateSaver = PasswordStateSaver) {
        mutableStateOf(PasswordState())
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .focusable(enabled = true)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SignUpLabel()

        Spacer(Modifier.height(32.dp))

        Login(loginState = loginState)

        Password(passwordState = passwordState)
    }
}

@Composable
internal fun Login(
    loginState: TextFieldState = remember { LoginState() },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
            )
            .onFocusChanged { focusState ->
                loginState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    loginState.enableShowErrors()
                }
            },
        value = loginState.text,
        onValueChange = { loginState.text = it },
        textStyle = MaterialTheme.typography.body2,
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            textColor = MaterialTheme.colors.onBackground,
            unfocusedBorderColor = White,
            cursorColor = MaterialTheme.colors.onBackground
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.body2
            )
        },
        isError = loginState.showErrors(),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_account),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(onDone = { onImeAction() })
    )
    Box(
        modifier = Modifier.height(16.dp)
    ) {
        loginState.getError()?.let { error -> TextFieldError(textError = error) }
    }
}

@Composable
internal fun Password(
    passwordState: TextFieldState = remember { PasswordState() }
) {
    var showPassword by remember { mutableStateOf(true) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
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
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            textColor = MaterialTheme.colors.onBackground,
            unfocusedBorderColor = White,
            cursorColor = MaterialTheme.colors.onBackground
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.password),
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
                modifier = Modifier.clickable {
                    showPassword = !showPassword
                },
                imageVector = if (showPassword) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
        }
    )
    Box(
        modifier = Modifier.height(16.dp)
    ) {
        passwordState.getError()?.let { error -> TextFieldError(textError = error) }
    }
}

@Composable
private fun SignUpLabel() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.signUp),
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
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.body2,
            fontSize = 14.sp
        )
    }
}
