package com.example.taskmanagement.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.auth.auth.navigation.authGraph
import com.example.auth.auth.navigation.authNavigationRouteGraph

@Composable
fun TMNavHost(
    navController: NavHostController,
    onOnboardingPassed: () -> Unit,
    shouldShowOnboarding: Boolean,
    modifier: Modifier = Modifier,
) {
    NavHost(
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
