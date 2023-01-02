package com.example.taskmanagement.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.auth.navigation.authGraph
import com.example.auth.navigation.navigateToAuth
import com.example.onboarding.navigation.onboardingNavigationRoute
import com.example.onboarding.navigation.onboardingScreen

@Composable
fun TMNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = onboardingNavigationRoute
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){
        onboardingScreen{
            navController.navigateToAuth()
        }
        authGraph()
    }
}