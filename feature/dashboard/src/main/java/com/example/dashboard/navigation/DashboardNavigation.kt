package com.example.dashboard.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.example.dashboard.home.DashboardRoute
import com.example.designsystem.utils.animationDuration
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation

const val dashboardGraph = "dashboard_graph"

const val dashboardHomeRoute = "home_route"

fun NavController.navigateToDashboard(navOptions: NavOptions? = null) {
    this.navigate(dashboardGraph, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.dashboardGraph(
    navController: NavController,
    signInRoute: String,
    signUpRoute: String,
) {
    navigation(
        route = dashboardGraph,
        startDestination = dashboardHomeRoute,
    ) {
        composable(
            dashboardHomeRoute,
            enterTransition = {
                slideIntoContainer(
                    if (initialState.destination.route == signInRoute || initialState.destination.route == signUpRoute)
                        AnimatedContentScope.SlideDirection.Up
                    else
                        AnimatedContentScope.SlideDirection.Right,
                    tween(animationDuration)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left, tween(animationDuration)
                )
            }
        ) {
            DashboardRoute()
        }
    }
}