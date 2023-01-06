package com.example.taskmanagement.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.auth.navigation.authGraph
import com.example.auth.navigation.authNavigationRouteGraph
import com.example.auth.navigation.navigateToSignIn
import com.example.auth.navigation.navigateToSignUp
import com.example.onboarding.navigation.onboardingScreen

@Composable
fun TMNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    onOnboardingPassed: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = authNavigationRouteGraph
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        onboardingScreen(
            navigateToSignUp = {
                onNavigateFromOnboardingScreen(onOnboardingPassed, navController)
                navController.navigateToSignUp()
            },
            navigateToSignIn = {
                onNavigateFromOnboardingScreen(onOnboardingPassed, navController)
                navController.navigateToSignIn()
            }
        )
        authGraph()
    }
}

private fun onNavigateFromOnboardingScreen(
    onOnboardingPassed: () -> Unit,
    navController: NavHostController
){
    onOnboardingPassed()
    navController.backQueue.removeLast()
}