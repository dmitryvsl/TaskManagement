package com.example.taskmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import com.example.data.utils.NetworkMonitor
import com.example.designsystem.theme.TaskManagementTheme
import com.example.taskmanagement.navigation.TMNavHost
import com.example.taskmanagement.utils.SharedPreferencesUtils
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    private val isOnline: MutableState<Boolean> = mutableStateOf(true)
    private var disposable: Disposable? = null

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        disposable =
            networkMonitor.isOnline.subscribe { isOnline -> this.isOnline.value = isOnline }

        setContent {
            TaskManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberAnimatedNavController()
                    val snackbarHostState = remember { SnackbarHostState() }
                    val notConnected = stringResource(R.string.notConnected)
                    val uiController = rememberSystemUiController()
                    uiController.setSystemBarsColor(darkIcons = !isSystemInDarkTheme(), color = Color.Transparent)
                    LaunchedEffect(isOnline.value) {
                        if (!isOnline.value)
                            snackbarHostState.showSnackbar(notConnected)
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
        disposable?.dispose()
        super.onDestroy()
    }
}
