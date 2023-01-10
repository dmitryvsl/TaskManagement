package com.example.notification.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.example.designsystem.utils.animationDuration
import com.example.notification.NotificationRoute
import com.google.accompanist.navigation.animation.composable

const val notificationRoute = "notification_route"

fun NavController.navigateToNotification(navOptions: NavOptions? = null) {
    this.navigate(notificationRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.notification(
    settingsRoute: String
) {
    composable(
        notificationRoute,
        enterTransition = {
            slideIntoContainer(
                if (initialState.destination.route == settingsRoute)
                    AnimatedContentScope.SlideDirection.Right
                else
                    AnimatedContentScope.SlideDirection.Left,
                tween(animationDuration)
            )

        },
        exitTransition = {
            slideOutOfContainer(
                if (targetState.destination.route == settingsRoute)
                    AnimatedContentScope.SlideDirection.Left
                else
                    AnimatedContentScope.SlideDirection.Right,
                tween(animationDuration)
            )
        }
    ) {
        NotificationRoute()
    }
}