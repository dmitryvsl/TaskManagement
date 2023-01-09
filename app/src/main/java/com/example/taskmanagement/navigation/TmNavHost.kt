package com.example.taskmanagement.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.auth.navigation.authGraph
import com.example.auth.navigation.authNavigationRouteGraph
import com.example.dashboard.navigation.dashboard
import com.example.dashboard.navigation.dashboardGraph
import com.example.dashboard.navigation.dashboardHomeRoute
import com.example.dashboard.navigation.navigateToDashboard
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TmNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    onOnboardingPassed: () -> Unit,
    shouldShowOnboarding: Boolean,
    shouldShowAuth: Boolean,
    modifier: Modifier = Modifier,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = if (shouldShowAuth) authNavigationRouteGraph else dashboardGraph,
        modifier = modifier
    ) {
        authGraph(
            navController = navController,
            shouldShowOnboarding = shouldShowOnboarding,
            modifier = modifier,
            onOnboardingPassed = onOnboardingPassed,
            navigateToDashboard = {
                navController.backQueue.clear()
                navController.navigateToDashboard()
            }
        )
        dashboard(
            navController = navController,
            onBackClick = onBackClick
        )
    }
}
