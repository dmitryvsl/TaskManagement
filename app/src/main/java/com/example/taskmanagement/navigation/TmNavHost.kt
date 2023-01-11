package com.example.taskmanagement.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.example.auth.navigation.authGraph
import com.example.auth.navigation.authNavigationRouteGraph
import com.example.auth.navigation.signInRoute
import com.example.auth.navigation.signUpRoute
import com.example.chat.navigation.chatGraph
import com.example.chat.navigation.chatListRoute
import com.example.dashboard.navigation.dashboardGraph
import com.example.dashboard.navigation.dashboardHomeRoute
import com.example.dashboard.navigation.navigateToDashboard
import com.example.notification.navigation.notification
import com.example.notification.navigation.notificationRoute
import com.example.settings.navigation.settingsGraph
import com.example.settings.navigation.settingsRoute
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TmNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    onOnboardingPassed: () -> Unit,
    onAuthPassed: () -> Unit,
    startDestination: String,
    authGraphStartDestination: String,
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
                    NavOptions.Builder().setPopUpTo(signUpRoute, true).build()
                )
            }
        )
        dashboardGraph(
            navController = navController,
            onBackClick = onBackClick,
            signInRoute = signInRoute,
            signUpRoute = signUpRoute,
        )
        chatGraph(dashboardRoute = dashboardHomeRoute)
        notification(settingsRoute = settingsRoute)
        settingsGraph()
    }
}
