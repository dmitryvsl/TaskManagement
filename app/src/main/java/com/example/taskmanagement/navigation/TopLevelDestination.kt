package com.example.taskmanagement.navigation

import androidx.annotation.DrawableRes
import com.example.feature.dashboard.R as dashboardR

enum class TopLevelDestination (
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
){
    DASHBOARD(
        selectedIcon = dashboardR.drawable.ic_dashboard_filled,
        unselectedIcon = dashboardR.drawable.ic_dashboard_outlined,
    )
}