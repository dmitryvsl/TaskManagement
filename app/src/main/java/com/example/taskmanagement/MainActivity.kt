package com.example.taskmanagement

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.data.utils.NetworkMonitor
import com.example.designsystem.theme.TaskManagementTheme
import com.example.taskmanagement.navigation.TMNavHost
import com.example.taskmanagement.utils.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    private val isOnline: MutableState<Boolean> = mutableStateOf(true)
    private val disposable by lazy(LazyThreadSafetyMode.NONE) {
        networkMonitor.isOnline.subscribe { isOnline -> this.isOnline.value = isOnline }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            TaskManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val snackbarHostState = remember { SnackbarHostState() }
                    LaunchedEffect(isOnline.value) {
                        if (!isOnline.value)
                            snackbarHostState.showSnackbar("Not Connected")
                    }
                    Scaffold(
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                    ) { paddingValues ->
                        TMNavHost(
                            modifier = Modifier.padding(paddingValues),
                            navController = navController,
                            shouldShowOnboarding = sharedPreferencesUtils.isFirstLaunch(),
                            onOnboardingPassed = { sharedPreferencesUtils.markOnboardingPassed() },
                        )
                    }

                }
            }
        }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}
