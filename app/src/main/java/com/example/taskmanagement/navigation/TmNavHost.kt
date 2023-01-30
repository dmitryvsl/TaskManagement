package com.example.taskmanagement.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.example.auth.navigation.authGraph
import com.example.chat.navigation.chatGraph
import com.example.dashboard.navigation.dashboardGraph
import com.example.dashboard.navigation.dashboardHomeRoute
import com.example.dashboard.navigation.navigateToDashboard
import com.example.notification.navigation.notification
import com.example.settings.navigation.settingsGraph
import com.example.settings.navigation.settingsRoute
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TmNavHost(
    navController: NavHostController,
    startDestination: String,
    authGraphStartDestination: String,
    onOnboardingPassed: () -> Unit,
    onAuthPassed: () -> Unit,
    navigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        authGraph(
            navController = navController,
            dashboardRoute = dashboardHomeRoute,
            modifier = modifier,
            startDestination = authGraphStartDestination,
            onOnboardingPassed = onOnboardingPassed,
            navigateToDashboard = {
                onAuthPassed()
                navController.navigateToDashboard(
                    NavOptions.Builder().setPopUpTo(0, true).build()
                )
            }
        )
        dashboardGraph(
            navController = navController,
            navigateToSettings = navigateToSettings
        )
        chatGraph(dashboardRoute = dashboardHomeRoute)
        notification(settingsRoute = settingsRoute)
        settingsGraph()
    }
}
