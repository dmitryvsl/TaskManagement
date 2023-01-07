package com.example.auth.auth.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.auth.auth.SignInRoute
import com.example.auth.auth.SignUpRoute
import com.example.auth.onboarding.OnboardingRoute


const val authNavigationRouteGraph = "auth_graph"
private const val signUpRoute = "auth_route"
private const val signInRoute = "signin_route"
private const val onboardingNavigationRoute = "onboarding_route"

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    this.navigate(signUpRoute, navOptions)
}

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) {
    this.navigate(signInRoute, navOptions)
}

fun NavGraphBuilder.authGraph(
    navController: NavController,
    shouldShowOnboarding: Boolean = false,
    modifier: Modifier = Modifier,
    onOnboardingPassed: () -> Unit
) {
    navigation(
        route = authNavigationRouteGraph,
        startDestination = if (shouldShowOnboarding) onboardingNavigationRoute else signUpRoute
    ) {
        composable(onboardingNavigationRoute) {
            OnboardingRoute(
                navigateToSignUp = {
                    onNavigateFromOnboardingScreen(onOnboardingPassed, navController)
                    navController.navigateToSignUp()
                },
                navigateToSignIn = {
                    onNavigateFromOnboardingScreen(onOnboardingPassed, navController)
                    navController.navigateToSignIn()
                }
            )
        }

        composable(signUpRoute) {
            SignUpRoute(
                modifier = modifier,
                onSignInClick = { navController.navigateToSignIn() },
                onSignUp = {}
            )
        }
        composable(signInRoute) {
            SignInRoute(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}

private fun onNavigateFromOnboardingScreen(
    onOnboardingPassed: () -> Unit,
    navController: NavController
) {
    onOnboardingPassed()
    navController.backQueue.removeLast()
}
