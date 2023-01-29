package com.example.dashboard.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.DataState
import com.example.dashboard.home.components.CurrentProject
import com.example.dashboard.home.components.SearchRow
import com.example.dashboard.home.components.SearchState
import com.example.designsystem.components.FeatureInDevelopment
import com.example.designsystem.components.TopLevelAppBar
import com.example.designsystem.components.card.TaskCard
import com.example.designsystem.components.information.ErrorMessageWithAction
import com.example.designsystem.components.information.InformationMessage
import com.example.designsystem.components.information.ListTitle
import com.example.designsystem.components.information.Loading
import com.example.designsystem.components.tab.TmTabLayout
import com.example.designsystem.theme.dimens
import com.example.domain.exception.InformationNotFound
import com.example.domain.exception.NoInternetException
import com.example.domain.exception.UserNotInWorkspace
import com.example.domain.model.Project
import com.example.feature.dashboard.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun DashboardRoute(
    viewModel: DashboardHomeViewModel = hiltViewModel(),
    navigateToProjectsList: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val state by viewModel.data.observeAsState(DataState.Initial())
    val contentModifier = Modifier.padding(horizontal = MaterialTheme.dimens.paddingExtraLarge)

    BoxWithConstraints(Modifier.statusBarsPadding()) {
        val screenHeight = maxHeight
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            val searchState: SearchState by remember { mutableStateOf(SearchState()) }

            Column(modifier = Modifier.height(screenHeight)) {
                val pagerState = rememberPagerState(initialPage = 0)
                val coroutineScope = rememberCoroutineScope()
                val tabs =
                    listOf(stringResource(R.string.overview), stringResource(R.string.analytics))
                val (selected, setSelected) = remember {
                    mutableStateOf(0)
                }
                LaunchedEffect(pagerState) {
                    snapshotFlow { pagerState.currentPage }.collect { page ->
                        setSelected(page)
                    }
                }

                TopLevelAppBar(
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.paddingExtraLarge),
                    title = stringResource(R.string.dashboard),
                    titleIcon = painterResource(R.drawable.ic_dashboard),
                    onActionButtonClick = {
                        // TODO: Add overlay menu 
                    }
                )

                SearchRow(modifier = contentModifier, searchState, focusManager)

                Tabs(contentModifier, tabs, selected) { page ->
                    setSelected(page)
                    coroutineScope.launch { pagerState.animateScrollToPage(page) }
                }

                HorizontalPager(
                    state = pagerState,
                    count = tabs.size,
                    modifier = Modifier
                        .fillMaxHeight()
                        .nestedScroll(remember {
                            object : NestedScrollConnection {
                                override fun onPreScroll(
                                    available: Offset,
                                    source: NestedScrollSource
                                ): Offset {
                                    return if (available.y > 0) Offset.Zero else Offset(
                                        x = 0f,
                                        y = -scrollState.dispatchRawDelta(-available.y)
                                    )
                                }
                            }
                        })
                ) { page ->
                    when (page) {
                        0 -> CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
                            DashboardHomeScreen(
                                modifier = contentModifier,
                                state = state,
                                onRetryLoading = { viewModel.fetchProject() },
                                navigateToProjectsList = navigateToProjectsList,
                                navigateToSettings = navigateToSettings
                            )
                        }

                        1 -> {
                            FeatureInDevelopment()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardHomeScreen(
    state: DataState<Project>,
    onRetryLoading: () -> Unit,
    navigateToProjectsList: () -> Unit,
    navigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {

    when (state) {
        is DataState.Initial -> {}
        is DataState.Error -> when (state.t) {
            is NoInternetException ->
                ErrorMessageWithAction(
                    message = stringResource(R.string.noInternet),
                    actionMessage = stringResource(R.string.tryAgain),
                    onActionClick = onRetryLoading
                )

            is InformationNotFound -> InformationMessage(text = stringResource(R.string.noOneProjectCreated))
            is UserNotInWorkspace -> ErrorMessageWithAction(
                message = stringResource(R.string.noWorkspace),
                actionMessage = stringResource(R.string.createWorkspace),
                onActionClick = navigateToSettings,
                modifier = modifier
            )
            else ->
                ErrorMessageWithAction(
                    message = stringResource(R.string.errorOccurred),
                    actionMessage = stringResource(R.string.tryAgain),
                    onActionClick = onRetryLoading
                )
        }

        is DataState.Success -> ProjectAndTasks(
            state.data,
            modifier = modifier,
            navigateToProjectsList = navigateToProjectsList
        )

        is DataState.Loading -> Loading(Modifier.fillMaxSize())
    }
}

@Composable
private fun ProjectAndTasks(
    project: Project,
    navigateToProjectsList: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val listTitleModifier =
        Modifier
            .clickable { navigateToProjectsList() }
            .padding(MaterialTheme.dimens.paddingExtraLarge)

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item("project_title") {
            ListTitle(
                title = stringResource(R.string.yourProject),
                titleIcon = painterResource(R.drawable.ic_project),
                modifier = listTitleModifier
            )
        }

        item(key = "project") {
            CurrentProject(
                project = project,
                modifier = modifier,
            )
        }

        item("task_title") {
            ListTitle(
                title = stringResource(id = R.string.yourRecentTasks),
                titleIcon = painterResource(id = R.drawable.ic_task),
                modifier = listTitleModifier
            )
        }

        item {
            if (project.tasks.isEmpty()) InformationMessage(
                text = stringResource(R.string.noOneTaskCreated),
                modifier = Modifier.fillMaxWidth()
            )
        }

        items(
            project.tasks,
            key = { task -> task.hashCode() }
        ) { task ->
            if (!task.done)
                TaskCard(
                    modifier = Modifier.padding(
                        horizontal = MaterialTheme.dimens.paddingExtraLarge,
                        vertical = MaterialTheme.dimens.paddingSmall
                    ),
                    icon = painterResource(R.drawable.ic_dashboard),
                    title = task.name,
                    date = task.endDate,
                    color = Color(task.color)
                )
        }
    }
}


@Composable
private fun Tabs(
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
