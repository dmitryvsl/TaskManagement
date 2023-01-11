package com.example.dashboard

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.designsystem.components.information.ListTitle
import com.example.designsystem.components.card.ProjectCard
import com.example.designsystem.components.textfield.TmOutlinedTextField
import com.example.designsystem.components.TopLevelAppBar
import com.example.designsystem.components.card.TaskCard
import com.example.designsystem.components.tab.TmTabLayout
import com.example.designsystem.components.textfield.TextFieldState
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.Purple
import com.example.designsystem.theme.dimens
import com.example.feature.dashboard.R

@OptIn(ExperimentalFoundationApi::class)
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

        val lazyColumnScrollState = rememberLazyListState()
        LazyColumn(
            state = lazyColumnScrollState
        ) {

            item {
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingDefault))
                Search(
                    modifier = modifier,
                    searchState = searchState,
                    onImeAction = { focusManager.clearFocus() }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingExtraLarge))
            }

            stickyHeader(key = "header") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                ) {
                    TmTabLayout(
                        modifier = modifier,
                        items = tabs,
                        selectedItemIndex = selected,
                        onClick = setSelected
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingExtraLarge))
            }

            item("project title") {
                ListTitle(
                    title = stringResource(R.string.yourProject),
                    titleIcon = painterResource(R.drawable.ic_project),
                    modifier = Modifier
                        .clickable { }
                        .padding(MaterialTheme.dimens.paddingExtraLarge)

                )
            }

            item(key = "project") {
                val offset = MaterialTheme.dimens.paddingMedium
                val isSystemDark = isSystemInDarkTheme()
                val firstLayerColor =
                    remember { if (isSystemDark) Purple.copy(alpha = 0.5f) else Gray.copy(alpha = 0.2f) }
                val secondLayerColor =
                    remember { if (isSystemDark) Purple.copy(alpha = 0.2f) else LightGray.copy(alpha = 0.5f) }
                Box(
                    modifier = Modifier
                        .padding(top = offset * 3)
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.dimens.paddingExtraLarge)
                            .height(offset * 4)
                            .padding(horizontal = offset * 2)
                            .offset(y = offset * 2 * -1f),
                        shape = MaterialTheme.shapes.medium,
                        color = secondLayerColor
                    ) {}
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(offset * 2)
                            .padding(horizontal = MaterialTheme.dimens.paddingExtraLarge)
                            .padding(horizontal = offset)
                            .offset(y = offset * -1f),
                        shape = MaterialTheme.shapes.medium,
                        color = firstLayerColor
                    ) {}

                    ProjectCard(modifier = modifier)
                }
            }

            item("task title") {
                ListTitle(
                    title = stringResource(id = R.string.yourRecentTasks),
                    titleIcon = painterResource(id = R.drawable.ic_task),
                    modifier = Modifier
                        .clickable { }
                        .padding(MaterialTheme.dimens.paddingExtraLarge)

                )
            }

            items(count = 2) { index ->
                TaskCard(
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.paddingExtraLarge, vertical = MaterialTheme.dimens.paddingSmall),
                    icon = painterResource(id = R.drawable.ic_dashboard),
                    title = "Task bhbh h hb hb h hh hb hbfafafawfawfawfwafwafawfawf $index",
                    date = "01/01/2023"
                )
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