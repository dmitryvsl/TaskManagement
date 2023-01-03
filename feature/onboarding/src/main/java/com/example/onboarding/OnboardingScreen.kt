package com.example.onboarding

import android.content.res.Configuration
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.TaskManagementTheme
import com.example.onboarding.components.PageLandscape
import com.example.onboarding.components.PagePortrait
import com.example.onboarding.model.Page
import com.example.onboarding.model.onboardPages
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun OnboardingRoute(
    navigateToAuth: () -> Unit,
    navigateToSignIn: () -> Unit
) {
    val pagerState = rememberPagerState()
    var isLastPage by remember { mutableStateOf(false) }
    var scrollEnabled by remember { mutableStateOf(true) }

    var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }
    val configuration = LocalConfiguration.current

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { currentPage ->
            if (currentPage == pagerState.pageCount - 1) {
                isLastPage = !isLastPage
                scrollEnabled = !scrollEnabled
            }
        }
    }

    if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        OnboardingLandscape(
            pagerState = pagerState,
            isLastPage = isLastPage,
            scrollEnabled = scrollEnabled,
            navigateToAuth = navigateToAuth
        )
    else
        OnboardingPortrait(
            pagerState = pagerState,
            isLastPage = isLastPage,
            scrollEnabled = scrollEnabled,
            navigateToAuth = navigateToAuth,
            navigateToSignIn = navigateToSignIn
        )
}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun OnboardingPortrait(
    pagerState: PagerState,
    isLastPage: Boolean,
    scrollEnabled: Boolean,
    navigateToAuth: () -> Unit,
    navigateToSignIn: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OnboardingHorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
            pagerState = pagerState,
            scrollEnabled = scrollEnabled
        ) { page ->
            PagePortrait(page = onboardPages[page])
        }

        AnimatedVisibility(visible = isLastPage) {
            GetStartedAndSignIn(
                navigateToAuth = navigateToAuth,
                navigateToSignIn = navigateToSignIn
            )
        }

        AnimatedVisibility(visible = !isLastPage) {
            SkipAndPagerIndicator(pagerState)
        }

    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun OnboardingLandscape(
    pagerState: PagerState,
    isLastPage: Boolean,
    scrollEnabled: Boolean,
    navigateToAuth: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        OnboardingHorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f),
            pagerState = pagerState,
            scrollEnabled = scrollEnabled
        ) { page ->
            PageLandscape(
                page = onboardPages[page],
                isLastPage = isLastPage,
                navigateToAuth = navigateToAuth
            )
        }
        AnimatedVisibility(visible = !isLastPage) {
            Spacer(modifier = Modifier.height(48.dp))
            SkipAndPagerIndicator(pagerState = pagerState)
        }
    }
}


@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
private fun OnboardingHorizontalPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    scrollEnabled: Boolean,
    content: @Composable (Int) -> Unit
) {
    // Remove overscroll effect
    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        HorizontalPager(
            modifier = modifier.systemBarsPadding(),
            count = onboardPages.size,
            state = pagerState,
            userScrollEnabled = scrollEnabled
        ) { page ->
            content(page)
        }
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
private fun SkipAndPagerIndicator(
    pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .size(72.dp)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                ),
                shape = CircleShape,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(onboardPages.size - 1)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowRight,
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = "Skip"
                )
            }
            Text(
                text = stringResource(id = R.string.skip),
                style = MaterialTheme.typography.h4,
                color = Gray
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = MaterialTheme.colors.primary,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Composable
internal fun GetStartedAndSignIn(
    navigateToAuth: () -> Unit,
    navigateToSignIn: () -> Unit,
    showSignInLabel: Boolean = true,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            onClick = navigateToAuth,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
            )
        ) {
            Text(
                text = stringResource(id = R.string.getStarted),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h4
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (showSignInLabel) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.alreadyHaveAnAccount),
                    style = MaterialTheme.typography.h4,
                    color = Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    modifier = Modifier.clickable { navigateToSignIn() },
                    text = stringResource(id = R.string.signIn),
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.secondary
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPortraitPreview() {
    TaskManagementTheme {
        OnboardingRoute(
            navigateToAuth = {},
            navigateToSignIn = {}
        )
    }
}