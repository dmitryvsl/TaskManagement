package com.example.notification.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.example.notification.NotificationRoute
import com.google.accompanist.navigation.animation.composable

const val notificationRoute = "notification_route"

fun NavController.navigateToNotification(navOptions: NavOptions? = null){
    this.navigate(notificationRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.notification(){
    composable(notificationRoute){
        NotificationRoute()
    }
}