package com.example.auth.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth.R
import com.example.designsystem.components.AlreadyHaveAnAccount
import com.example.designsystem.components.TextFieldState
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.Red
import com.example.designsystem.theme.White

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpRoute() {
    val loginState by remember { mutableStateOf(LoginState()) }
    val passwordState: PasswordState by remember { mutableStateOf(PasswordState()) }
    val confirmPasswordState by remember { mutableStateOf(ConfirmPasswordState(passwordState)) }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        SignUpLabel()
        Spacer(Modifier.height(24.dp))

        Login(loginState = loginState)

        Password(
            passwordState = passwordState,
            hint = R.string.password,
        )

        // Confirm password
        Password(
            passwordState = confirmPasswordState,
            hint = R.string.confirm_password,
            imeAction = ImeAction.Done,
            onImeAction = { keyboardController?.hide() }
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            onClick = {
                /*TODO*/
            }
        ) {
            Text(
                text = stringResource(id = R.string.signUp),
                style = MaterialTheme.typography.h4,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Divider(color = LightGray, thickness = 2.dp)
            Text(
                modifier = Modifier
                    .background(color = MaterialTheme.colors.background)
                    .padding(horizontal = 16.dp),
                text = stringResource(id = R.string.orSignUpWith),
                style = MaterialTheme.typography.h4,
                color = Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SocialRow()

        Spacer(modifier = Modifier.weight(1f))

        AlreadyHaveAnAccount { }

        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
private fun SocialRow() {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        SocialIcon(onClick = { /*TODO*/ }, icon = R.drawable.ic_facebook)
        Spacer(modifier = Modifier.width(16.dp))
        SocialIcon(onClick = { /*TODO*/ }, icon = R.drawable.ic_instagram)
        Spacer(modifier = Modifier.width(16.dp))
        SocialIcon(onClick = { /*TODO*/ }, icon = R.drawable.ic_gmail)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SocialIcon(
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    contentDescription: String? = null,
) {
    Surface(
        modifier = Modifier.size(48.dp),
        color = LightGray,
        shape = MaterialTheme.shapes.small,
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.padding(12.dp),
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            tint = MaterialTheme.colors.onBackground
        )
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
                shape = MaterialTheme.shapes.medium,
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
        shape = MaterialTheme.shapes.medium,
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
        keyboardActions = KeyboardActions(onDone = { onImeAction() }),
    )
    Box(
        modifier = Modifier.height(20.dp)
    ) {
        loginState.getError()?.let { error -> TextFieldError(textError = error) }
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
            fontSize = 12.sp
        )
    }
}
