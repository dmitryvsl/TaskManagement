package com.example.dashboard.project_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dashboard.home.components.Search
import com.example.dashboard.home.components.SearchState
import com.example.designsystem.components.BookmarkOverlay
import com.example.designsystem.components.appbar.TmTopAppBar
import com.example.designsystem.components.card.ProjectCard
import com.example.designsystem.components.information.ErrorMessageWithAction
import com.example.designsystem.components.information.InformationMessage
import com.example.designsystem.components.information.Loading
import com.example.designsystem.components.tab.TmTabLayout
import com.example.designsystem.theme.dimens
import com.example.domain.exception.InformationNotFound
import com.example.domain.exception.NoInternetException
import com.example.domain.model.DataState
import com.example.domain.model.Project
import com.example.feature.dashboard.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProjectListRoute(
    viewModel: ProjectListViewModel = hiltViewModel(), onBackClick: () -> Unit
) {
    val allProjectsState by viewModel.data.collectAsState()
    val completedProjectsState by viewModel.completedProjects.collectAsState()
    val bookmarkedProjectsState by viewModel.bookmarkedProjects.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .systemBarsPadding(),
    ) {
        val modifier = Modifier.padding(horizontal = MaterialTheme.dimens.paddingExtraLarge)

        TmTopAppBar(modifier = modifier,
            title = stringResource(R.string.project),
            onBackClick = onBackClick,
            action = {
                Button(
                    modifier = Modifier.size(MaterialTheme.dimens.minimumTouchTarget),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            })

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingDefault))

        val searchState = remember { SearchState() }
        Search(modifier = modifier, searchState = searchState)

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingDefault))

        val (selected, setSelected) = remember { mutableStateOf(0) }

        val tabs = listOf(
            stringResource(R.string.projects),
            stringResource(R.string.completed),
            stringResource(R.string.flag)
        )
        val pagerState = rememberPagerState(initialPage = selected)
        val coroutineScope = rememberCoroutineScope()
        TmTabLayout(modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimens.paddingExtraSmall)
            .align(Alignment.CenterHorizontally),
            selectedItemIndex = selected,
            items = tabs,
            onClick = { page ->
                coroutineScope.launch { pagerState.animateScrollToPage(page) }
            }
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingSmall))

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page -> setSelected(page) }
        }
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top,
            count = tabs.size,
            state = pagerState
        ) { page ->
            when (page) {
                0 -> ProjectListScreen(
                    modifier = modifier,
                    state = allProjectsState,
                    onRetryLoading = { viewModel.fetchProjects() },
                    onBookmarkClick = { project -> viewModel.onBookmarkClick(project) }
                )

                1 -> ProjectListScreen(
                    modifier = modifier,
                    state = completedProjectsState,
                    onRetryLoading = { viewModel.fetchCompleted() },
                    messageForInformationNotFound = stringResource(R.string.noOneProjectCompleted),
                    onBookmarkClick = { project -> viewModel.onBookmarkClick(project) }
                )

                2 -> ProjectListScreen(
                    modifier = modifier,
                    state = bookmarkedProjectsState,
                    onRetryLoading = { viewModel.fetchBookmarks() },
                    messageForInformationNotFound = stringResource(R.string.bookmarkProjectToSee),
                    onBookmarkClick = { project -> viewModel.onBookmarkClick(project) }
                )
            }
        }
    }
}

@Composable
fun ProjectListScreen(
    state: DataState<List<Project>>,
    onRetryLoading: () -> Unit,
    onBookmarkClick: (Project) -> Unit,
    modifier: Modifier = Modifier,
    messageForInformationNotFound: String = stringResource(R.string.noOneProjectCreated)
) {
    var showOverlay by remember { mutableStateOf(false) }
    when (state) {
        is DataState.Initial -> {}
        is DataState.Loading -> Loading(modifier = Modifier.fillMaxSize())
        is DataState.Error -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (state.t) {
                is NoInternetException -> ErrorMessageWithAction(
                    message = stringResource(R.string.noInternet),
                    actionMessage = stringResource(R.string.tryAgain),
                    onActionClick = onRetryLoading
                )

                is InformationNotFound -> InformationMessage(messageForInformationNotFound)
                else -> ErrorMessageWithAction(
                    message = stringResource(R.string.errorOccurred),
                    actionMessage = stringResource(R.string.tryAgain),
                    onActionClick = onRetryLoading
                )
            }
        }

        is DataState.Success -> LazyColumn {
            items(items = state.data, key = { project -> project.title }) { project ->
                ProjectCard(
                    project = project,
                    modifier = modifier.padding(vertical = MaterialTheme.dimens.paddingMedium),
                    onLongClick = { showOverlay = true },
                    overlay = {
                        if (showOverlay) BookmarkOverlay(
                            isBookmarked = project.isBookmarked,
                            onCloseClick = { showOverlay = false },
                            onBookmarkClick = {
                                showOverlay = false
                                onBookmarkClick(project)
                            }
                        )
                    }
                )
            }
        }
    }
}
