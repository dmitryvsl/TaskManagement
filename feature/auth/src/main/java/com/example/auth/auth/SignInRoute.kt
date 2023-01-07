package com.example.auth.auth

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.common.components.AuthOtherWay
import com.example.feature.auth.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInRoute(
    onBackClick: () -> Unit
) {
    val email = remember { EmailState() }
    val password = remember { PasswordState() }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { focusManager.clearFocus() }
                )
            }
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        IconButton(
            onClick = onBackClick,
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colors.background
            )
        }

        Spacer(Modifier.weight(1f))

        AuthHeader(R.string.signIn)

        Spacer(Modifier.height(24.dp))

        Email(emailState = email)

        Password(
            passwordState = password,
            hint = R.string.password,
            imeAction = ImeAction.Done,
            onImeAction = {
                keyboardController?.hide()
            }
        )

        Text(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .clip(MaterialTheme.shapes.small),
            text = stringResource(R.string.forgotPassword),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

            },
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = stringResource(R.string.signIn))
        }

        Spacer(Modifier.weight(1f))

        AuthOtherWay(
            hint = R.string.dontHaveAccount,
            authWayName = R.string.signUp,
            onAuthWayClick = onBackClick
        )

        Spacer(Modifier.height(16.dp))
    }
}