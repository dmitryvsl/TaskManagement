package com.example.chat.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.example.chat.ChatRoute
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation

const val chatGraph = "chat_graph"

const val chatListRoute = "chat_list"

fun NavController.navigateToChat(navOptions: NavOptions? = null) {
    this.navigate(chatGraph, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.chatGraph() {
    navigation(startDestination = chatListRoute, route = chatGraph) {
        composable(chatListRoute) {
            ChatRoute()
        }
    }
}