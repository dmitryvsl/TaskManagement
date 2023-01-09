package com.example.taskmanagement

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.example.chat.navigation.chatListRoute
import com.example.chat.navigation.navigateToChat
import com.example.dashboard.navigation.dashboardHomeRoute
import com.example.dashboard.navigation.navigateToDashboard
import com.example.notification.navigation.navigateToNotification
import com.example.notification.navigation.notificationRoute
import com.example.settings.navigation.navigateToSettings
import com.example.settings.navigation.settingsRoute
import com.example.taskmanagement.navigation.TopLevelDestination
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberAppState(
    navController: NavHostController = rememberAnimatedNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    uiController: SystemUiController = rememberSystemUiController()

): AppState {
    return remember(navController, snackbarHostState, uiController) {
        AppState(navController, snackbarHostState, uiController)
    }
}

class AppState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    val uiController: SystemUiController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            dashboardHomeRoute -> TopLevelDestination.DASHBOARD
            chatListRoute -> TopLevelDestination.CHAT
            notificationRoute -> TopLevelDestination.NOTIFICATION
            settingsRoute -> TopLevelDestination.SETTINGS
            else -> null
        }

    // Show Bottom Bar only in Top Level Destinations
    val shouldShowBottomBar: Boolean
        @Composable get() = currentDestination?.route in topLevelDestinationRoutes


    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    // Routes to Top Level Destination composables
    val topLevelDestinationRoutes: List<String> =
        listOf(dashboardHomeRoute, chatListRoute, notificationRoute, settingsRoute)

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {

        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.DASHBOARD -> navController.navigateToDashboard(topLevelNavOptions)
            TopLevelDestination.CHAT -> navController.navigateToChat(topLevelNavOptions)
            TopLevelDestination.NOTIFICATION ->
                navController.navigateToNotification(topLevelNavOptions)
            TopLevelDestination.SETTINGS -> navController.navigateToSettings(topLevelNavOptions)
        }

    }

    fun onBackClick() {
        navController.popBackStack()
    }
}
