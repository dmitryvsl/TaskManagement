package com.example.dashboard.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.example.dashboard.DashboardRoute
import com.google.accompanist.navigation.animation.composable

const val dashboardGraph = "dashboard_graph"

const val dashboardHomeRoute = "home_route"

fun NavController.navigateToDashboard(navOptions: NavOptions? = null) {
    this.navigate(dashboardGraph, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.dashboardGraph(
    navController: NavController,
    onBackClick: () -> Unit,
    signInRoute: String,
    signUpRoute: String,
) {
    navigation(
        route = dashboardGraph,
        startDestination = dashboardHomeRoute
    ) {
        composable(
            dashboardHomeRoute,
            enterTransition = {
                if (targetState.destination.hierarchy.any { it.route == signInRoute || it.route == signUpRoute })
                    slideInVertically { 1000 }
                else
                    slideInHorizontally { -1000 }
            }
        ) {
            DashboardRoute()
        }
    }
}