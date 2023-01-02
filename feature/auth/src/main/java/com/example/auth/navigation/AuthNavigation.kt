package com.example.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation


private const val authNavigationRouteGraph = "auth_graph"
const val signUpRoute = "auth_route"

fun NavController.navigateToAuth(){
    this.navigate(authNavigationRouteGraph)
}

fun NavGraphBuilder.authGraph(){
    navigation(
        route = authNavigationRouteGraph,
        startDestination = signUpRoute
    ){
        composable(signUpRoute){
            SignUpRoute()
        }
    }
}