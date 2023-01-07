package com.example.auth.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.auth.SignInRoute
import com.example.auth.SignUpRoute
import com.example.data.utils.NetworkMonitor


const val authNavigationRouteGraph = "auth_graph"
private const val signUpRoute = "auth_route"
private const val signInRoute = "signin_route"
fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    this.navigate(signUpRoute, navOptions)
}

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) {
    this.navigate(signInRoute, navOptions)
}

fun NavGraphBuilder.authGraph(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    navigation(
        route = authNavigationRouteGraph,
        startDestination = signUpRoute
    ) {
        composable(signUpRoute) {
            SignUpRoute(
                modifier = modifier,
                onSignInClick = { navController.navigateToSignIn() },
                onSignUp = {}
            )
        }
        composable(signInRoute) {
            SignInRoute()
        }
    }
}