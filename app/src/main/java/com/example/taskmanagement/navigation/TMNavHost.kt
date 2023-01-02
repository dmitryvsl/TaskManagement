package com.example.taskmanagement.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.auth.navigation.authGraph
import com.example.auth.navigation.authNavigationRouteGraph
import com.example.auth.navigation.navigateToAuth
import com.example.auth.navigation.signUpRoute
import com.example.onboarding.navigation.onboardingNavigationRoute
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
        onboardingScreen {
            onOnboardingPassed()
            navController.backQueue.removeLast()
            navController.navigateToAuth()
        }
        authGraph()
    }
}