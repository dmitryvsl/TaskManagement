package com.example.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.designsystem.components.AlreadyHaveAnAccount
import com.example.designsystem.theme.Gray
import com.example.feature.onboarding.R
import com.example.onboarding.model.Page
import com.example.onboarding.model.onboardPages
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
internal fun OnboardingRoute(
    navigateToSignIn: () -> Unit,
    navigateToSignUp: () -> Unit
) {
    val pagerState = rememberPagerState()
    var isLastPage by remember { mutableStateOf(false) }
    var scrollEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { currentPage ->
            if (currentPage == pagerState.pageCount - 1) {
                isLastPage = !isLastPage
                scrollEnabled = !scrollEnabled
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .systemBarsPadding(),
                count = onboardPages.size,
                state = pagerState,
                userScrollEnabled = scrollEnabled
            ) { page ->
                PageUI(page = onboardPages[page])
            }
        }

        AnimatedVisibility(visible = !isLastPage) {
            Spacer(modifier = Modifier.weight(1f))
            SkipAndPagerIndicator(
                pagerState = pagerState
            )
        }
        AnimatedVisibility(visible = isLastPage) {
            GetStartedAndSignIn(
                navigateToSignIn = navigateToSignIn,
                navigateToSignUp = navigateToSignUp
            )
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
private fun PageUI(
    page: Page
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(220.dp),
            painter = painterResource(page.image),
            contentDescription = "Onboarding image"
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = stringResource(page.title),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground
        )

        Text(
            text = stringResource(page.description),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun GetStartedAndSignIn(
    navigateToSignIn: () -> Unit,
    navigateToSignUp: () -> Unit,
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
            onClick = navigateToSignUp,
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

        AlreadyHaveAnAccount { navigateToSignIn() }

        Spacer(modifier = Modifier.width(8.dp))
    }
}
