package com.example.dashboard.project_list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dashboard.home.components.Search
import com.example.dashboard.home.components.SearchState
import com.example.designsystem.components.card.ProjectCard
import com.example.designsystem.components.information.ErrorMessageWithAction
import com.example.designsystem.components.information.InformationMessage
import com.example.designsystem.components.information.Loading
import com.example.designsystem.components.tab.TmTabLayout
import com.example.designsystem.theme.dimens
import com.example.domain.exception.InformationNotFound
import com.example.domain.exception.NoInternetException
import com.example.domain.model.Project
import com.example.feature.dashboard.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProjectListRoute(
    viewModel: ProjectListViewModel = hiltViewModel(), onBackClick: () -> Unit
) {
    val projects by viewModel.data.observeAsState()
    val loading by viewModel.loading.observeAsState(false)
    val error by viewModel.error.observeAsState()
    val selectedTab by viewModel.currentTab.observeAsState(Tab.INITIAL)

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
            onActionClick = {})

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingDefault))

        val searchState = remember { SearchState() }
        Search(
            modifier = modifier, searchState = searchState
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingDefault))


        val tabs = listOf(
            stringResource(R.string.projects),
            stringResource(R.string.completed),
            stringResource(R.string.flag)
        )
        TmTabLayout(modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimens.paddingExtraSmall)
            .align(Alignment.CenterHorizontally),
            selectedItemIndex = selectedTab.ordinal,
            items = tabs,
            onClick = { page -> viewModel.onPageChange(Tab.values()[page]) })

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingSmall))

        val pagerState = rememberPagerState(initialPage = 0)
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page -> viewModel.onPageChange(Tab.values()[page]) }
        }
        HorizontalPager(
            count = tabs.size, state = pagerState
        ) { page ->
            ProjectListScreen(modifier = modifier,
                loading = loading,
                projects = projects,
                error = error,
                onRetryLoading = { viewModel.fetchProjects() })
        }
    }
}

@Composable
fun ProjectListScreen(
    loading: Boolean,
    projects: List<Project>?,
    error: Throwable?,
    onRetryLoading: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (loading) Loading(modifier = Modifier.fillMaxSize())

    error?.let { error ->
        when (error) {
            is NoInternetException -> ErrorMessageWithAction(
                message = stringResource(R.string.noInternet),
                actionMessage = stringResource(R.string.tryAgain),
                onActionClick = onRetryLoading
            )

            is InformationNotFound -> InformationMessage(stringResource(R.string.noOneProjectCreated))
            else -> ErrorMessageWithAction(
                message = stringResource(R.string.errorOccurred),
                actionMessage = stringResource(R.string.tryAgain),
                onActionClick = onRetryLoading
            )
        }
    }

    projects?.let { projectList ->
        LazyColumn {
            items(items = projectList, key = { project -> project.title }) { project ->
                ProjectCard(
                    project = project,
                    modifier = modifier.padding(vertical = MaterialTheme.dimens.paddingMedium)
                )
            }
        }
    }
}

@Composable
private fun TmTopAppBar(
    title: String, onBackClick: () -> Unit, onActionClick: () -> Unit, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(modifier = Modifier
            .size(MaterialTheme.dimens.minimumTouchTarget)
            .clip(CircleShape)
            .clickable { onBackClick() }
            .padding(MaterialTheme.dimens.paddingSmall),
            imageVector = Icons.Rounded.KeyboardArrowLeft,
            contentDescription = null,
            tint = MaterialTheme.colors.onBackground)
        Text(
            text = title,
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.onBackground
        )
        Button(
            modifier = Modifier.size(MaterialTheme.dimens.minimumTouchTarget),
            onClick = onActionClick,
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
    }
}