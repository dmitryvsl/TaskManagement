package com.example.auth.auth.signup

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth.auth.*
import com.example.designsystem.components.AuthOtherWay
import com.example.designsystem.theme.*
import com.example.feature.auth.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpRoute(
    navigateToSignIn: () -> Unit,
    onSignUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val loginState by remember { mutableStateOf(EmailState()) }
    val passwordState: PasswordState by remember { mutableStateOf(PasswordState()) }
    val confirmPasswordState by remember { mutableStateOf(ConfirmPasswordState(passwordState)) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    CallStateRepresenter(viewModel = viewModel, onCallSuccess = onSignUp)

    Column(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        AuthHeader(label = R.string.signUp)

        Spacer(Modifier.height(24.dp))

        Email(emailState = loginState)

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
            enabled = loginState.isValid && passwordState.isValid && confirmPasswordState.isValid,
            onClick = {
                keyboardController?.hide()
                viewModel.createUser(loginState.text, passwordState.text)
            }
        ) {
            Text(
                text = stringResource(id = R.string.signUp),
                style = MaterialTheme.typography.h4,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        AuthOtherWay(
            hint = R.string.alreadyHaveAnAccount,
            authWayName = R.string.signIn
        ) { navigateToSignIn() }

        Spacer(modifier = Modifier.weight(1f))

    }
}