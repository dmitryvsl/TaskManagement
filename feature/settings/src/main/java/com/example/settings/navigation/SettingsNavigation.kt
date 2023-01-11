package com.example.settings.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.example.designsystem.utils.animationDuration
import com.google.accompanist.navigation.animation.navigation
import com.example.settings.SettingsRoute
import com.google.accompanist.navigation.animation.composable

const val settingsGraph = "settings_graph"

const val settingsRoute = "settings_route"

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    this.navigate(settingsGraph, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.settingsGraph() {
    navigation(
        startDestination = settingsRoute,
        route = settingsGraph,
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, tween(animationDuration))
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, tween(animationDuration))
        }
    ) {
        composable(settingsRoute) {
            SettingsRoute()
        }
    }
}