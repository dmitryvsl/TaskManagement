package com.example.taskmanagement.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.auth.navigation.authGraph
import com.example.auth.navigation.authNavigationRouteGraph
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TMNavHost(
    navController: NavHostController,
    onOnboardingPassed: () -> Unit,
    shouldShowOnboarding: Boolean,
    modifier: Modifier = Modifier,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = authNavigationRouteGraph,
        modifier = modifier
    ) {
        authGraph(
            navController = navController,
            shouldShowOnboarding = shouldShowOnboarding,
            modifier = modifier,
            onOnboardingPassed = onOnboardingPassed
        )
    }
}
