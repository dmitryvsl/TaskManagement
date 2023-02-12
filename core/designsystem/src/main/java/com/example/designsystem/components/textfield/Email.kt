package com.example.designsystem.components.textfield

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.core.designsystem.R
import com.example.designsystem.theme.dimens
import java.util.regex.Pattern

private const val EMAIL_VALIDATION_REGEX = "^(.+)@(.+)\$"
class EmailState : TextFieldState(::isLoginValid, ::loginValidationError)

private fun isLoginValid(email: String) = Pattern.matches(EMAIL_VALIDATION_REGEX, email)

private fun loginValidationError(email: String): String = "Invalid email"

/**
 * Textfield for entering email
 */
@Composable
fun Email(
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