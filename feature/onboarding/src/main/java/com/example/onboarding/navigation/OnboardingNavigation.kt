package com.example.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.onboarding.OnboardingRoute

const val onboardingNavigationRoute = "onboarding_route"

fun NavGraphBuilder.onboardingScreen(
    navigateToSignUp: () -> Unit,
    navigateToSignIn: () -> Unit
) {
    composable(route = onboardingNavigationRoute) {
        OnboardingRoute(navigateToSignUp = navigateToSignUp, navigateToSignIn = navigateToSignIn)
    }
}