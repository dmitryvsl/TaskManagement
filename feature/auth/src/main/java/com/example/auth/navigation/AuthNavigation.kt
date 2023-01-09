package com.example.auth.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.example.auth.auth.signin.SignInRoute
import com.example.auth.auth.forgotpassword.ForgotPasswordRoute
import com.example.auth.auth.signup.SignUpRoute
import com.example.auth.onboarding.OnboardingRoute
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation


const val authNavigationRouteGraph = "auth_graph"
private const val signUpRoute = "auth_route"
private const val signInRoute = "signin_route"
private const val onboardingRoute = "onboarding_route"
private const val forgotPasswordRoute = "forgot_password_route"

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    this.navigate(signUpRoute, navOptions)
}

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) {
    this.navigate(signInRoute, navOptions)
}

fun NavController.navigateToForgotPassword(navOptions: NavOptions? = null) {
    this.navigate(forgotPasswordRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authGraph(
    navController: NavController,
    shouldShowOnboarding: Boolean = false,
    modifier: Modifier = Modifier,
    onOnboardingPassed: () -> Unit,
    navigateToDashboard: () -> Unit
) {
    navigation(
        route = authNavigationRouteGraph,
        startDestination = if (shouldShowOnboarding) onboardingRoute else signUpRoute,
        enterTransition = {
            slideInHorizontally { 1000 }

        },
        exitTransition = {
            slideOutHorizontally { -1000 }

        },
        popEnterTransition = {
            slideInHorizontally { -1000 }


        },
        popExitTransition = {
            slideOutHorizontally { 1000 }
        }
    ) {
        composable(onboardingRoute) {
            OnboardingRoute(
                navigateToSignUp = {
                    onOnboardingPassed()
                    navController.navigateToSignUp(
                        NavOptions.Builder()
                            .setPopUpTo(route = onboardingRoute, inclusive = true)
                            .build()
                    )
                },
                navigateToSignIn = {
                    onOnboardingPassed()
                    navController.navigateToSignIn(
                        NavOptions.Builder()
                            .setPopUpTo(route = onboardingRoute, inclusive = true)
                            .build()
                    )
                }
            )
        }

        composable(signUpRoute) {
            SignUpRoute(
                modifier = modifier,
                navigateToSignIn = { navController.navigateToSignIn() },
                onSignUp = navigateToDashboard
            )
        }
        composable(signInRoute) {
            SignInRoute(
                onBackClick = { navController.navigateUp() },
                onSignIn = navigateToDashboard,
                navigateToForgotPassword = { navController.navigateToForgotPassword() }
            )
        }

        composable(forgotPasswordRoute) {
            ForgotPasswordRoute(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}
