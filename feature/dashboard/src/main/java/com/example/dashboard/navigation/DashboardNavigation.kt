package com.example.dashboard.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.example.dashboard.home.DashboardRoute
import com.example.dashboard.project_list.ProjectListRoute
import com.example.designsystem.utils.animationDuration
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import kotlin.math.roundToInt

const val dashboardGraph = "dashboard_graph"

const val dashboardHomeRoute = "home_route"

const val projectsList = "projects_list"

fun NavController.navigateToDashboard(navOptions: NavOptions? = null) {
    this.navigate(dashboardGraph, navOptions)
}

private fun NavController.navigateToProjectsList(navOptions: NavOptions? = null) {
    this.navigate(projectsList, navOptions)
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
                if (initialState.destination.route == projectsList) return@composable null

                slideIntoContainer(
                    when (initialState.destination.route){
                        signInRoute -> AnimatedContentScope.SlideDirection.Up
                        signUpRoute -> AnimatedContentScope.SlideDirection.Up
                        else -> AnimatedContentScope.SlideDirection.Right
                    },
                    tween(animationDuration)
                )
            },
            exitTransition = {
                if (targetState.destination.route != projectsList)
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Left, tween(animationDuration)
                    )
                else null
            }
        ) {
            DashboardRoute(navigateToProjectsList = { navController.navigateToProjectsList(NavOptions.Builder().setLaunchSingleTop(true).build()) })
        }

        composable(
            route = projectsList,
            enterTransition = {
                val animationDuration = animationDuration / 2
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    tween(animationDuration)
                )
            },
            popExitTransition = {
                val animationDuration = animationDuration / 2
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right, tween(animationDuration)
                )
            }
        ) {
            ProjectListRoute {

            }
        }
    }
}