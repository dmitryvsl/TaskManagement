package com.example.onboarding.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.onboarding.GetStartedAndSignIn
import com.example.onboarding.model.Page


@Composable
internal fun PagePortrait(
    page: Page
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OnboardingImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.65f),
            image = page.image
        )

        Spacer(modifier = Modifier.height(48.dp))

        OnboardingTitle(page.title)
        OnboardingDescription(page.description)

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
internal fun PageLandscape(
    page: Page,
    isLastPage: Boolean,
    navigateToAuth: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .padding(24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        OnboardingImage(
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .fillMaxHeight(),
            image = page.image
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OnboardingTitle(page.title)
            OnboardingDescription(page.description)

            Spacer(modifier = Modifier.height(16.dp))
            if (isLastPage) {
                GetStartedAndSignIn(
                    navigateToAuth = navigateToAuth,
                    navigateToSignIn = {},
                    showSignInLabel = false
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

        }
    }
}

@Composable
private fun OnboardingImage(
    modifier: Modifier,
    @DrawableRes image: Int
) {
    Image(
        modifier = modifier,
        painter = painterResource(image),
        contentDescription = "Onboarding image"
    )
}

@Composable
private fun OnboardingTitle(@StringRes title: Int) {
    Text(
        text = stringResource(title),
        style = MaterialTheme.typography.h1,
        color = MaterialTheme.colors.onBackground,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun OnboardingDescription(@StringRes description: Int) {
    Text(
        text = stringResource(description),
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onBackground,
        textAlign = TextAlign.Center
    )
}
