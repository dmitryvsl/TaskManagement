package com.example.taskmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.auth.navigation.authNavigationRouteGraph
import com.example.designsystem.theme.TaskManagementTheme
import com.example.onboarding.navigation.onboardingNavigationRoute
import com.example.taskmanagement.navigation.TMNavHost
import com.example.taskmanagement.utils.SharedPreferencesUtils
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferencesUtils: SharedPreferencesUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        (application as App).component.inject(this)

        setContent {
            TaskManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    TMNavHost(
                        navController = navController,
                        onBackClick = {},
                        onOnboardingPassed = { sharedPreferencesUtils.markNotFirstLaunch() },
                        startDestination = defineStartDestination()
                    )
                }
            }
        }
    }

    private fun defineStartDestination(): String {
        if (!sharedPreferencesUtils.isFirstLaunch()) return authNavigationRouteGraph
        return onboardingNavigationRoute
    }
}
