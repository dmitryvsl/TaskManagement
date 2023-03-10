package com.example.auth.auth.forgotpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth.auth.DataStateRepresenter
import com.example.designsystem.components.overlay.InformationDialog
import com.example.designsystem.components.textfield.Email
import com.example.designsystem.components.textfield.EmailState
import com.example.designsystem.theme.dimens
import com.example.feature.auth.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPasswordRoute(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val modifier = Modifier.padding(horizontal = MaterialTheme.dimens.paddingExtraLarge)
    val emailState = remember { EmailState() }
    var showInformationOverlay by remember { mutableStateOf(false) }

    if (showInformationOverlay) InformationDialog(message = stringResource(R.string.messageWasSentToYourEmail)) {
        showInformationOverlay = !showInformationOverlay
        onBackClick()
    }

    DataStateRepresenter(
        viewModel = viewModel,
        onCallSuccess = { showInformationOverlay = !showInformationOverlay },
        invalidEmailOrPasswordExceptionMessage = stringResource(R.string.invalidEmail)
    )

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

        Image(
            modifier = modifier.size(100.dp),
            painter = painterResource(R.drawable.forgot_password),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingDefault))

        Text(
            modifier = modifier,
            text = stringResource(R.string.forgotPassword),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(MaterialTheme.dimens.paddingSmall))

        Text(
            modifier = modifier,
            text = stringResource(id = R.string.enterYourEmailToResetPassword),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingDefault))

        Email(
            modifier = modifier,
            emailState = emailState,
            imeAction = ImeAction.Done,
            onImeAction = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        )

        Button(
            modifier = modifier.fillMaxWidth(),
            enabled = emailState.isValid,
            onClick = {
                focusManager.clearFocus()
                viewModel.sendForgotPasswordEmail(emailState.text)
            }
        ) {
            Text(
                text = stringResource(id = R.string.sendEmail),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onPrimary
            )
        }

        Spacer(modifier = Modifier.weight(4f))

    }
}