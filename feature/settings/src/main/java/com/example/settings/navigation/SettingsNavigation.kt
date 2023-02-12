package com.example.settings.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.example.designsystem.utils.animationDuration
import com.example.settings.SettingsRoute
import com.example.settings.createworkspace.CreateWorkspaceScreen
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation

const val settingsGraph = "settings_graph"

const val settingsRoute = "settings_route"
private const val createWorkspaceRoute = "create_workspace_route"

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    this.navigate(settingsGraph, navOptions)
}

private fun NavController.navigateToCreateWorkspace(navOptions: NavOptions? = null) {
    this.navigate(createWorkspaceRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.settingsGraph(
    navController: NavController
) {
    navigation(
        startDestination = settingsRoute,
        route = settingsGraph,
    ) {
        composable(
            route = settingsRoute,
            enterTransition = {
                if (initialState.destination.route == createWorkspaceRoute) return@composable null
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, tween(animationDuration))
            },
            exitTransition = {
                if (targetState.destination.route == createWorkspaceRoute) return@composable null
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, tween(animationDuration))
            }) {
            SettingsRoute(
                navigateToCreateWorkspace = { navController.navigateToCreateWorkspace() }
            )
        }
        composable(
            route = createWorkspaceRoute,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    tween(animationDuration / 2)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    tween(animationDuration / 2)
                )
            }
        ) {
            CreateWorkspaceScreen{
                navController.navigateUp()
            }
        }
    }
}