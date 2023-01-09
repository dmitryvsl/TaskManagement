package com.example.auth.auth.signin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth.auth.*
import com.example.designsystem.components.AuthOtherWay
import com.example.feature.auth.R


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInRoute(
    viewModel: SignInViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onSignIn: () -> Unit,
    navigateToForgotPassword: () -> Unit
) {
    val email = remember { EmailState() }
    val password = remember { PasswordState() }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val modifier: Modifier = Modifier.padding(horizontal = 24.dp)

    CallStateRepresenter(viewModel = viewModel, onCallSuccess = onSignIn)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.statusBarsPadding())
        IconButton(
            modifier = Modifier
                .size(48.dp)
                .padding(12.dp)
                .align(Alignment.Start),
            onClick = onBackClick,
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIos,
                contentDescription = "Back",
                tint = MaterialTheme.colors.onBackground
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        AuthHeader(label = R.string.signIn)

        Spacer(Modifier.height(24.dp))

        Email(modifier = modifier, emailState = email)

        Password(
            modifier = modifier,
            passwordState = password,
            hint = R.string.password,
            imeAction = ImeAction.Done,
            onImeAction = {
                keyboardController?.hide()
            }
        )

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .clip(MaterialTheme.shapes.small)
                    .clickable { navigateToForgotPassword() },
                text = stringResource(R.string.forgotPassword),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground
            )
        }

        Button(
            modifier = modifier.fillMaxWidth(),
            onClick = {
                keyboardController?.hide()
                viewModel.signInUser(email.text, password.text)
            },
            enabled = email.isValid && password.isValid,
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = stringResource(R.string.signIn),
                style = MaterialTheme.typography.h4,
            )
        }

        Spacer(Modifier.height(16.dp))

        AuthOtherWay(
            modifier = modifier,
            hint = R.string.dontHaveAccount,
            authWayName = R.string.signUp,
            onAuthWayClick = onBackClick
        )

        Spacer(modifier = Modifier.weight(4f))
    }
}