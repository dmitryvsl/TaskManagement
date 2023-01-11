package com.example.taskmanagement.navigation

import androidx.annotation.DrawableRes
import com.example.feature.dashboard.R as dashboardR
import com.example.feature.chat.R as chatR
import com.example.feature.notification.R as notificationR
import com.example.feature.settings.R as settingsR

enum class TopLevelDestination(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
) {
    DASHBOARD(
        selectedIcon = dashboardR.drawable.ic_dashboard_filled,
        unselectedIcon = dashboardR.drawable.ic_dashboard_outlined,
    ),
    CHAT(
        selectedIcon = chatR.drawable.ic_chat_filled,
        unselectedIcon = chatR.drawable.ic_chat_outlined,
    ),
    NOTIFICATION(
        selectedIcon = notificationR.drawable.ic_notification_filled,
        unselectedIcon = notificationR.drawable.ic_notification_outlined,
    ),
    SETTINGS(
        selectedIcon = settingsR.drawable.ic_settings_filled,
        unselectedIcon = settingsR.drawable.ic_settings_outlined,
    )
}