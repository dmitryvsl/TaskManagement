package com.example.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.auth.SignInRoute


const val authNavigationRouteGraph = "auth_graph"
private const val signUpRoute = "auth_route"
private const val signInRoute = "signin_route"
fun NavController.navigateToAuth(navOptions: NavOptions? = null){
    this.navigate(authNavigationRouteGraph, navOptions)
}

fun NavController.navigateToSignIn(navOptions: NavOptions? = null){
    this.navigate(signInRoute, navOptions)
}

fun NavGraphBuilder.authGraph(){
    navigation(
        route = authNavigationRouteGraph,
        startDestination = signUpRoute
    ){
        composable(signUpRoute){
            SignUpRoute()
        }
        composable(signInRoute){
            SignInRoute()
        }
    }
}