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
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.designsystem.components.information.ListTitle
import com.example.designsystem.components.card.ProjectCard
import com.example.designsystem.components.textfield.TmOutlinedTextField
import com.example.designsystem.components.TopLevelAppBar
import com.example.designsystem.components.card.TaskCard
import com.example.designsystem.components.information.Loading
import com.example.designsystem.components.tab.TmTabLayout
import com.example.designsystem.components.textfield.TextFieldState
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.Purple
import com.example.designsystem.theme.dimens
import com.example.feature.dashboard.R
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardRoute(
    viewModel: DashboardHomeViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val modifier = Modifier.padding(horizontal = MaterialTheme.dimens.paddingExtraLarge)
    val project by viewModel.project.observeAsState()
    val loading by viewModel.loading.observeAsState()
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
    ) {
        val searchState: SearchState by remember { mutableStateOf(SearchState()) }
        val tabs = listOf(stringResource(R.string.overview), stringResource(R.string.analytics))
        val (selected, setSelected) = remember {
            mutableStateOf(0)
        }

        val listTitleModifier =
            Modifier
                .clickable { }
                .padding(MaterialTheme.dimens.paddingExtraLarge)

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
            item { SearchRow(modifier, searchState, focusManager) }

            stickyHeader(key = "header") { HeaderRow(modifier, tabs, selected, setSelected) }

            item("project title") {
                ListTitle(
                    title = stringResource(R.string.yourProject),
                    titleIcon = painterResource(R.drawable.ic_project),
                    modifier = listTitleModifier

                )
            }

            item(key = "project") {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .height(150.dp + MaterialTheme.dimens.paddingMedium * 3)
                ) {
                    loading?.let { loading ->
                        if (loading) Loading(Modifier.fillMaxSize())
                    }
                    project?.let { project ->
                        val completedTasks =
                            remember { project.tasks.filter { task -> task.done }.size }
                        ProjectRow(
                            modifier = modifier,
                            title = project.title,
                            startDate = project.startDate,
                            endDate = project.endDate,
                            completedTasks = completedTasks,
                            totalTasks = project.tasks.size
                        )
                    }
                }
            }

            item("task title") {
                ListTitle(
                    title = stringResource(id = R.string.yourRecentTasks),
                    titleIcon = painterResource(id = R.drawable.ic_task),
                    modifier = listTitleModifier
                )
            }

            loading?.let { loading ->
                if (loading) item {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) { Loading(Modifier.fillMaxSize()) }
                }
            }
            project?.let { project ->
                project.tasks.forEach { task ->
                    if (!task.done)
                        item(task.hashCode()) {
                            TaskCard(
                                modifier = Modifier.padding(
                                    horizontal = MaterialTheme.dimens.paddingExtraLarge,
                                    vertical = MaterialTheme.dimens.paddingSmall
                                ),
                                icon = painterResource(com.example.core.designsystem.R.drawable.ic_calendar),
                                title = task.name,
                                date = task.endDate
                            )
                        }
                }
            }
        }

    }


}

@Composable
private fun HeaderRow(
    modifier: Modifier,
    tabs: List<String>,
    selected: Int,
    setSelected: (Int) -> Unit
) {
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

@Composable
private fun SearchRow(
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
fun ProjectRow(
    modifier: Modifier,
    title: String,
    startDate: String,
    endDate: String,
    completedTasks: Int,
    totalTasks: Int,
) {
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
        repeat(2) { index ->
            val scale = index + 1
            Canvas(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.paddingExtraLarge)
                    .height(offset * 2 * scale)
                    .padding(horizontal = offset * scale)
                    .offset(y = offset * scale * -1f)
            ) {
                drawRoundRect(
                    color = if (index == 0) firstLayerColor else secondLayerColor,
                    topLeft = Offset.Zero,
                    cornerRadius = CornerRadius(16f, 16f),
                    size = this.size,
                )
            }
        }

        ProjectCard(
            modifier = modifier,
            title = title,
            startDate = startDate,
            endDate = endDate,
            completedTasks = completedTasks,
            totalTasks = totalTasks
        )
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