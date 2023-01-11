package com.example.auth.onboarding

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
import com.example.designsystem.components.AuthOtherWay
import com.example.designsystem.theme.Gray
import com.example.auth.onboarding.model.Page
import com.example.auth.onboarding.model.onboardPages
import com.example.designsystem.theme.dimens
import com.example.feature.auth.R
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
            .padding(horizontal = MaterialTheme.dimens.paddingSmall),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .size(MaterialTheme.dimens.minimumTouchTarget * 1.5f)
                    .padding(MaterialTheme.dimens.paddingSmall),
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
            modifier = Modifier.padding(MaterialTheme.dimens.paddingDefault),
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
            .padding(MaterialTheme.dimens.paddingExtraLarge),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(220.dp),
            painter = painterResource(page.image),
            contentDescription = "Onboarding image"
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingExtraLarge))

        Text(
            text = stringResource(page.title),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(page.description),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingDefault))
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
            .padding(horizontal = MaterialTheme.dimens.paddingExtraLarge)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.dimens.paddingSmall),
            shape = RoundedCornerShape(MaterialTheme.dimens.paddingDefault),
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

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingExtraLarge))

        AuthOtherWay(
            hint = R.string.alreadyHaveAnAccount,
            authWayName = R.string.signIn,
            onAuthWayClick = navigateToSignIn
        )

        Spacer(modifier = Modifier.width(MaterialTheme.dimens.paddingSmall))
    }
}
