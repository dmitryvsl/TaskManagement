package com.example.auth.auth.signin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth.auth.*
import com.example.designsystem.components.information.AuthOtherWay
import com.example.designsystem.components.textfield.Email
import com.example.designsystem.components.textfield.EmailState
import com.example.designsystem.theme.dimens
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

    val modifier: Modifier = Modifier.padding(horizontal = MaterialTheme.dimens.paddingDefault)

    DataStateRepresenter(viewModel = viewModel, onCallSuccess = onSignIn)

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
        Icon(modifier = Modifier
            .size(MaterialTheme.dimens.minimumTouchTarget)
            .clip(CircleShape)
            .clickable { onBackClick() }
            .padding(MaterialTheme.dimens.paddingSmall)
            .align(Alignment.Start),
            imageVector = Icons.Rounded.KeyboardArrowLeft,
            contentDescription = null,
            tint = MaterialTheme.colors.onBackground)

        Spacer(modifier = Modifier.weight(1f))

        AuthHeader(label = R.string.signIn)

        Spacer(Modifier.height(MaterialTheme.dimens.paddingExtraLarge))

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
                    .padding(bottom = MaterialTheme.dimens.paddingDefault)
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

        Spacer(Modifier.height(MaterialTheme.dimens.paddingDefault))

        AuthOtherWay(
            modifier = modifier,
            hint = R.string.dontHaveAccount,
            authWayName = R.string.signUp,
            onAuthWayClick = onBackClick
        )

        Spacer(modifier = Modifier.weight(4f))
    }
}