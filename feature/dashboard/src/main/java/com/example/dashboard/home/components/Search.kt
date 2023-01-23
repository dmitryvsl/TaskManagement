package com.example.dashboard.home.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.designsystem.components.textfield.TextFieldState
import com.example.designsystem.components.textfield.TmOutlinedTextField
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.dimens
import com.example.feature.dashboard.R


@Composable
internal fun SearchRow(
    modifier: Modifier,
    searchState: SearchState,
    focusManager: FocusManager
) {
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingDefault))
    Search(
        modifier = modifier,
        searchState = searchState,
        onImeAction = { focusManager.clearFocus() }
    )
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingExtraLarge))
}


@Composable
fun Search(
    modifier: Modifier = Modifier,
    searchState: TextFieldState = remember { SearchState() },
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {}
) {

    TmOutlinedTextField(
        modifier = modifier,
        value = searchState.text,
        onValueChange = { searchState.text = it },
        isError = searchState.showErrors(),
        onFocusChanged = { focusState ->
            searchState.onFocusChange(focusState.isFocused)
            if (!focusState.isFocused) searchState.enableShowErrors()
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                style = MaterialTheme.typography.body2
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = if (isSystemInDarkTheme()) LightGray else Gray
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone = { onImeAction() }
        )
    )
}