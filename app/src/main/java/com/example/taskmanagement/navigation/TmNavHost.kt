package com.example.taskmanagement.navigation

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.auth.navigation.authGraph
import com.example.auth.navigation.authNavigationRouteGraph
import com.example.auth.navigation.signInRoute
import com.example.auth.navigation.signUpRoute
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
    onBackClick: () -> Unit,
    onOnboardingPassed: () -> Unit,
    onAuthPassed: () -> Unit,
    shouldShowOnboarding: Boolean,
    shouldShowAuth: Boolean,
    modifier: Modifier = Modifier,
) {
    Log.d("TAG", "TmNavHost: $shouldShowAuth")
    AnimatedNavHost(
        navController = navController,
        startDestination = if (shouldShowAuth) authNavigationRouteGraph else dashboardGraph,
        modifier = modifier,
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
        authGraph(
            navController = navController,
            shouldShowOnboarding = shouldShowOnboarding,
            modifier = modifier,
            onOnboardingPassed = onOnboardingPassed,
            navigateToDashboard = {
                onAuthPassed()
                navController.backQueue.clear()
                navController.navigateToDashboard()
            }
        )
        dashboardGraph(
            navController = navController,
            onBackClick = onBackClick,
            signInRoute = signInRoute,
            signUpRoute = signUpRoute
        )
        chatGraph(dashboardRoute = dashboardHomeRoute)
        notification(settingsRoute = settingsRoute)
        settingsGraph()
    }
}
