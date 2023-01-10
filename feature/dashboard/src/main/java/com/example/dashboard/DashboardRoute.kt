package com.example.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.designsystem.components.ListTitle
import com.example.designsystem.components.textfield.TmOutlinedTextField
import com.example.designsystem.components.TopLevelAppBar
import com.example.designsystem.components.tab.TmTabLayout
import com.example.designsystem.components.textfield.TextFieldState
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.dimens
import com.example.feature.dashboard.R

@Composable
fun DashboardRoute() {
    val focusManager = LocalFocusManager.current
    val modifier = Modifier.padding(horizontal = MaterialTheme.dimens.paddingExtraLarge)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
    ) {
        val searchState: SearchState by remember { mutableStateOf(SearchState()) }
        val tabs = listOf(stringResource(R.string.overview), stringResource(R.string.analytics))
        val (selected, setSelected) = remember {
            mutableStateOf(0)
        }

        TopLevelAppBar(
            modifier = modifier,
            title = stringResource(R.string.dashboard),
            titleIcon = painterResource(R.drawable.ic_dashboard),
            onActionButtonClick = {
                // TODO: Add Action
            }
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingDefault))

        Search(
            modifier = modifier,
            searchState = searchState,
            onImeAction = { focusManager.clearFocus() }
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingExtraLarge))

        TmTabLayout(
            modifier = modifier,
            items = tabs,
            selectedItemIndex = selected,
            onClick = setSelected
        )

        val lazyColumnScrollState = rememberLazyListState()
        LazyColumn(
            state = lazyColumnScrollState
        ) {
            item {
                ListTitle(
                    title = stringResource(R.string.yourProject),
                    titleIcon = painterResource(R.drawable.ic_project),
                    modifier = Modifier
                        .clickable { }
                        .padding(MaterialTheme.dimens.paddingExtraLarge)
                )
            }

            items(count = 50) {
                repeat(50) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text(
                            text = "It's $index"
                        )
                    }
                }
            }
        }

    }


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