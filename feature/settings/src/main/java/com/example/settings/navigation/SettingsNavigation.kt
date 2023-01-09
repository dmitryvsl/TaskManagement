package com.example.settings.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.example.settings.SettingsRoute
import com.google.accompanist.navigation.animation.composable

const val settingsGraph = "settings_graph"

const val settingsRoute = "settings_route"

fun NavController.navigateToSettings(navOptions: NavOptions? = null){
    this.navigate(settingsGraph, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.settingsGraph(){
    navigation(
        startDestination = settingsRoute,
        route = settingsGraph
    ){
        composable(settingsRoute){
            SettingsRoute()
        }
    }
}